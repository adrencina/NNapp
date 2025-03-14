package com.example.nnapp.utils.pdfutils

import android.content.ContentValues
import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.example.nnapp.ui.viewmodel.BudgetFinalizeViewModel
import javax.inject.Inject

/**
 * PdfGenerator se encarga de crear el PDF del presupuesto usando MediaStore para guardarlo en la carpeta Downloads.
 * Esto es ideal para Android 10+ ya que se adapta al Scoped Storage.
 */
class PdfGenerator @Inject constructor() {

    /**
     * Crea un PDF a partir del estado final del presupuesto y lo guarda en la carpeta Downloads usando MediaStore.
     * @param context Contexto de la app.
     * @param state Estado del presupuesto con todos los datos necesarios (cliente, materiales, opciones finales).
     * @return URI del PDF guardado o null si falla.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun createPdf(context: Context, state: BudgetFinalizeViewModel.BudgetFinalizeUiState): Uri? {
        return try {
            // Crear el documento PDF y la primera página (tamaño A4)
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            val paint = Paint()

            var yPosition = 100f

            // Encabezado: título del documento
            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("Presupuesto", 100f, yPosition, paint)
            yPosition += 40f

            // Datos del cliente
            paint.textSize = 14f
            paint.isFakeBoldText = false
            canvas.drawText("Cliente: ${state.clientName}", 100f, yPosition, paint)
            yPosition += 30f
            canvas.drawText("Dirección: ${state.clientAddress}", 100f, yPosition, paint)
            yPosition += 30f

            // Lista de materiales
            if (state.materials.isEmpty()) {
                canvas.drawText("No se han ingresado materiales.", 100f, yPosition, paint)
            } else {
                canvas.drawText("Materiales:", 100f, yPosition, paint)
                yPosition += 20f
                state.materials.forEach { material ->
                    canvas.drawText("- $material", 120f, yPosition, paint)
                    yPosition += 20f
                }
            }

            // Opciones finales (si están habilitadas)
            if (state.hasExpiryDate) {
                canvas.drawText("Validez: ${state.expiryDate}", 100f, yPosition, paint)
                yPosition += 30f
            }
            if (state.hasPaymentMethods) {
                canvas.drawText("Formas de Pago: ${state.paymentMethods.joinToString()}", 100f, yPosition, paint)
                yPosition += 30f
            }
            if (state.hasComment) {
                canvas.drawText("Comentario: ${state.comment}", 100f, yPosition, paint)
            }

            pdfDocument.finishPage(page)

            // Configurar ContentValues para MediaStore
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, "presupuesto_${System.currentTimeMillis()}.pdf")
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            // Insertar en MediaStore para obtener un URI válido
            val uri: Uri? = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let { outUri ->
                resolver.openOutputStream(outUri)?.use { outputStream ->
                    pdfDocument.writeTo(outputStream)
                }
            }

            pdfDocument.close()
            uri
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}