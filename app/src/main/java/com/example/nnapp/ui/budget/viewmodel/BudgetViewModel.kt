package com.example.nnapp.ui.budget.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nnapp.data.model.Client
import com.example.nnapp.data.model.Material
import com.example.nnapp.data.repository.ClientRepository
import com.example.nnapp.domain.usecase.CreateBudgetUseCase
import com.example.nnapp.domain.usecase.GeneratePdfUseCase
import com.example.nnapp.domain.usecase.ShareBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val createBudgetUseCase: CreateBudgetUseCase,
    private val generatePdfUseCase: GeneratePdfUseCase,
    private val shareBudgetUseCase: ShareBudgetUseCase,
    private val clientRepository: ClientRepository
) : ViewModel() {

    // Estado del flujo (1: datos del cliente, 2: materiales, 3: resumen)
    var currentStep = mutableIntStateOf(1)

    // Datos del cliente
    var clientName = mutableStateOf("")
    var address = mutableStateOf("")
    var dni = mutableStateOf("")
    var phone = mutableStateOf("")
    var isFinalConsumer = mutableStateOf(false)

    // Materiales
    private val _materials = MutableStateFlow<List<Material>>(emptyList())
    val materials: StateFlow<List<Material>> = _materials.asStateFlow()

    // Configuraciones finales
    var paymentMethod = mutableStateOf("")
    var offerValidityDate = mutableStateOf("")

    fun isValidStep(): Boolean {
        return when (currentStep.intValue) {
            1 -> clientName.value.isNotBlank() && address.value.isNotBlank() && dni.value.length == 8 && phone.value.length >= 7
            2 -> materials.value.isNotEmpty()
            3 -> paymentMethod.value.isNotBlank() && offerValidityDate.value.isNotBlank()
            else -> false
        }
    }

    fun nextStep() {
        if (currentStep.intValue < 3) {
            currentStep.intValue++
        }
    }

    fun prevStep() {
        if (currentStep.intValue > 1) {
            currentStep.intValue--
        }
    }

    fun addMaterial(material: Material) {
        _materials.update { it + material }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun generatePdf(context: Context): Uri? {
        return generatePdfUseCase(context, this)
    }

    fun sharePdf(pdfPath: String) {
        // La lógica de compartir se implementa usando shareBudgetUseCase, por ejemplo obteniendo un Intent.
    }

    /**
     * Crea el presupuesto. Si el cliente no es "Consumidor Final", se crea un nuevo registro de cliente;
     * de lo contrario, se utiliza un cliente predefinido.
     */
    suspend fun createBudget(): String {
        val client: Client = if (isFinalConsumer.value) {
            Client(
                id = "consumidor_final",
                name = clientName.value.trim(),
                address = address.value.trim(),
                dni = dni.value.trim(),
                phone = phone.value.trim()
            )
        } else {
            val newClient = Client(
                name = clientName.value.trim(),
                address = address.value.trim(),
                dni = dni.value.trim(),
                phone = phone.value.trim()
            )
            val clientId = clientRepository.createClient(newClient)
            newClient.copy(id = clientId)
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        return createBudgetUseCase(client, materials.value, currentDate)
    }
}