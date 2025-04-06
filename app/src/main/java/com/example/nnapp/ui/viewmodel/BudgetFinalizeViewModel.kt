//package com.example.nnapp.ui.viewmodel
//
//import android.content.Context
//import android.net.Uri
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.nnapp.data.model.Budget
//import com.example.nnapp.utils.pdfutils.PdfGenerator
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
///**
// * ViewModel para la etapa final del presupuesto.
// * Se encarga de gestionar las opciones finales (fecha de validez, formas de pago, comentario)
// * y de generar el PDF usando la información completa (datos del cliente, materiales y opciones finales).
// */
//@HiltViewModel
//class BudgetFinalizeViewModel @Inject constructor(
//    private val pdfGenerator: PdfGenerator
//) : ViewModel() {
//
//    // Estado de la UI para la generación del PDF.
//    private val _uiState = MutableStateFlow(BudgetFinalizeUiState())
//    val uiState: StateFlow<BudgetFinalizeUiState> = _uiState
//
//    /**
//     * Carga los datos reales del presupuesto (cliente y materiales) desde el modelo de dominio.
//     * Se formatea la lista de materiales para mostrar: nombre (cantidad x precio).
//     * Se marca que el PDF aún no está generado para forzar su regeneración.
//     */
//    fun loadBudgetData(budget: Budget) {
//        val materialsFormatted = budget.materials.map { "${it.name} (${it.quantity} x ${it.unitPrice})" }
//        _uiState.value = _uiState.value.copy(
//            clientName = budget.clientName,
//            clientAddress = budget.address,
//            materials = materialsFormatted,
//            // Opciones finales se dejan en false/ vacías para que el usuario las configure
//            hasExpiryDate = false,
//            expiryDate = "",
//            hasPaymentMethods = false,
//            paymentMethods = emptyList(),
//            hasComment = false,
//            comment = "",
//            pdfGenerated = false
//        )
//    }
//
//    // Métodos para actualizar las opciones finales.
//    // Cada uno actualiza el estado y marca que el PDF debe regenerarse.
//    fun toggleExpiryDate(enabled: Boolean) {
//        _uiState.value = _uiState.value.copy(hasExpiryDate = enabled, pdfGenerated = false)
//    }
//
//    fun setExpiryDate(date: String) {
//        _uiState.value = _uiState.value.copy(expiryDate = date, pdfGenerated = false)
//    }
//
//    fun togglePaymentMethods(enabled: Boolean) {
//        _uiState.value = _uiState.value.copy(hasPaymentMethods = enabled, pdfGenerated = false)
//    }
//
//    fun setPaymentMethods(methods: List<String>) {
//        _uiState.value = _uiState.value.copy(paymentMethods = methods, pdfGenerated = false)
//    }
//
//    fun toggleComment(enabled: Boolean) {
//        _uiState.value = _uiState.value.copy(hasComment = enabled, pdfGenerated = false)
//    }
//
//    fun setComment(comment: String) {
//        _uiState.value = _uiState.value.copy(comment = comment, pdfGenerated = false)
//    }
//
//    /**
//     * Genera y guarda el PDF únicamente si aún no se generó (o si se modificaron los datos).
//     * Se evita la regeneración innecesaria.
//     */
//    @RequiresApi(Build.VERSION_CODES.Q)
//    fun generateAndSavePdf(context: Context) {
//        viewModelScope.launch {
//            // Si ya se generó el PDF, no se vuelve a generar.
//            if (_uiState.value.pdfGenerated) return@launch
//
//            // Se verifica que existan materiales (dato fundamental)
//            if (_uiState.value.materials.isEmpty()) {
//                println("🚨 ERROR: No se generó el PDF porque la lista de materiales está vacía")
//                return@launch
//            }
//
//            val pdfUri = pdfGenerator.createPdf(context, _uiState.value)
//            _uiState.value = _uiState.value.copy(pdfUri = pdfUri, pdfGenerated = true)
//        }
//    }
//
//    /**
//     * Función que guarda y comparte el PDF en una acción.
//     * Si el PDF ya existe, se comparte directamente; de lo contrario, se genera primero.
//     */
//    @RequiresApi(Build.VERSION_CODES.Q)
//    fun saveAndSharePdf(context: Context, onShare: (Uri?) -> Unit) {
//        viewModelScope.launch {
//            val pdfUri = _uiState.value.pdfUri ?: pdfGenerator.createPdf(context, _uiState.value)
//            _uiState.value = _uiState.value.copy(pdfUri = pdfUri, pdfGenerated = true)
//            onShare(pdfUri)
//        }
//    }
//
//    /**
//     * Estado de la UI para la generación del PDF.
//     * Inicialmente, los datos se dejan vacíos para que sean llenados con la información real.
//     */
//    data class BudgetFinalizeUiState(
//        val clientName: String = "",
//        val clientAddress: String = "",
//        val materials: List<String> = emptyList(),
//        val hasExpiryDate: Boolean = false,
//        val expiryDate: String = "",
//        val hasPaymentMethods: Boolean = false,
//        val paymentMethods: List<String> = emptyList(),
//        val hasComment: Boolean = false,
//        val comment: String = "",
//        val pdfUri: Uri? = null,
//        val pdfGenerated: Boolean = false
//    )
//}