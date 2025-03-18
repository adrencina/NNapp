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
import java.lang.Exception
import javax.inject.Inject

/**
 * PdfGenerator se encarga de crear el PDF del presupuesto usando MediaStore (carpeta Downloads).
 * Combina el formato que ya manejabas con una estructura más profesional:
 *  - Logo/nombre de empresa a la izquierda (placeholder)
 *  - Nº presupuesto y fecha a la derecha
 *  - Título "PRESUPUESTO" centrado
 *  - Datos del cliente
 *  - Lista de materiales
 *  - Opciones finales (fecha de validez, forma de pago, comentario)
 *  - Guarda en MediaStore y retorna el Uri del PDF
 */
class PdfGenerator @Inject constructor() {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun createPdf(context: Context, state: BudgetFinalizeViewModel.BudgetFinalizeUiState): Uri? {
        return try {
            // Tamaño aproximado de A4 en puntos (595x842) con margen
            val pageWidth = 595
            val pageHeight = 842
            val margin = 40f

            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            val paint = Paint()

            // Dimensiones y posición
            var yPosition = margin

            // Simulación de datos extra (ejemplo) - ajusta según tu lógica
            val budgetNumber = "PR: 0000-00039132"
            val budgetDate = "Fecha: 23/04/2024"
            // Podrías tener un phone = "4218800", etc.

            // 1) Logo y/o nombre de empresa (Placeholder)
            // Podrías dibujar un logo si lo tienes. Aquí dibujamos texto de ejemplo.
            paint.textSize = 16f
            paint.isFakeBoldText = true
            canvas.drawText("ELECTRO SERVICIOS S.A.", margin, yPosition, paint)
            yPosition += 25f

            // 2) Número de presupuesto y fecha (arriba a la derecha)
            paint.textSize = 12f
            paint.isFakeBoldText = false
            val xRightColumn = pageWidth - margin - 140f
            canvas.drawText(budgetNumber, xRightColumn, margin, paint)
            canvas.drawText(budgetDate, xRightColumn, margin + 15f, paint)

            // 3) Título "PRESUPUESTO" centrado
            paint.textSize = 18f
            paint.isFakeBoldText = true
            val title = "PRESUPUESTO"
            val titleWidth = paint.measureText(title)
            canvas.drawText(title, (pageWidth - titleWidth) / 2, yPosition + 10f, paint)
            yPosition += 50f

            // 4) Datos del cliente
            paint.textSize = 14f
            paint.isFakeBoldText = true
            canvas.drawText("DATOS CLIENTE", margin, yPosition, paint)
            yPosition += 20f

            paint.isFakeBoldText = false
            canvas.drawText("Cliente: ${state.clientName}", margin, yPosition, paint)
            yPosition += 18f
            canvas.drawText("Dirección: ${state.clientAddress}", margin, yPosition, paint)
            yPosition += 18f

            // Si quieres mostrar un teléfono, lo podrías agregar
            // canvas.drawText("Teléfono: 4218800", margin, yPosition, paint)
            // yPosition += 18f

            yPosition += 20f

            // 5) Sección DETALLE
            paint.isFakeBoldText = true
            canvas.drawText("DETALLE", margin, yPosition, paint)
            yPosition += 20f
            paint.isFakeBoldText = false

            // 6) Lista de materiales
            if (state.materials.isEmpty()) {
                canvas.drawText("No se han ingresado materiales.", margin, yPosition, paint)
                yPosition += 20f
            } else {
                // Título "Materiales:" (opcional)
                canvas.drawText("Materiales:", margin, yPosition, paint)
                yPosition += 20f

                state.materials.forEach { material ->
                    canvas.drawText("- $material", margin + 20f, yPosition, paint)
                    yPosition += 18f
                }
            }

            // 7) Opciones finales
            // Validez
            if (state.hasExpiryDate) {
                yPosition += 20f
                canvas.drawText("Validez: ${state.expiryDate}", margin, yPosition, paint)
                yPosition += 18f
            }
            // Formas de pago
            if (state.hasPaymentMethods) {
                yPosition += 20f
                canvas.drawText("Formas de Pago: ${state.paymentMethods.joinToString()}", margin, yPosition, paint)
                yPosition += 18f
            }
            // Comentario
            if (state.hasComment) {
                yPosition += 20f
                canvas.drawText("Comentario: ${state.comment}", margin, yPosition, paint)
                yPosition += 18f
            }

            // 8) Finalizar la página
            pdfDocument.finishPage(page)

            // 9) Guardar en MediaStore
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, "presupuesto_${System.currentTimeMillis()}.pdf")
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val resolver = context.contentResolver
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



//package com.example.nnapp.utils.pdfutils
//
//import android.content.ContentValues
//import android.content.Context
//import android.graphics.Paint
//import android.graphics.pdf.PdfDocument
//import android.net.Uri
//import android.os.Build
//import android.os.Environment
//import android.provider.MediaStore
//import androidx.annotation.RequiresApi
//import com.example.nnapp.ui.viewmodel.BudgetFinalizeViewModel
//import javax.inject.Inject
//
///**
// * PdfGenerator se encarga de crear el PDF del presupuesto usando MediaStore para guardarlo en la carpeta Downloads.
// * Esto es ideal para Android 10+ ya que se adapta al Scoped Storage.
// */
//class PdfGenerator @Inject constructor() {
//
//    /**
//     * Crea un PDF a partir del estado final del presupuesto y lo guarda en la carpeta Downloads usando MediaStore.
//     * @param context Contexto de la app.
//     * @param state Estado del presupuesto con todos los datos necesarios (cliente, materiales, opciones finales).
//     * @return URI del PDF guardado o null si falla.
//     */
//    @RequiresApi(Build.VERSION_CODES.Q)
//    fun createPdf(context: Context, state: BudgetFinalizeViewModel.BudgetFinalizeUiState): Uri? {
//        return try {
//            // Crear el documento PDF y la primera página (tamaño A4)
//            val pdfDocument = PdfDocument()
//            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
//            val page = pdfDocument.startPage(pageInfo)
//            val canvas = page.canvas
//            val paint = Paint()
//
//            var yPosition = 100f
//
//            // Encabezado: título del documento
//            paint.textSize = 18f
//            paint.isFakeBoldText = true
//            canvas.drawText("Presupuesto", 100f, yPosition, paint)
//            yPosition += 40f
//
//            // Datos del cliente
//            paint.textSize = 14f
//            paint.isFakeBoldText = false
//            canvas.drawText("Cliente: ${state.clientName}", 100f, yPosition, paint)
//            yPosition += 30f
//            canvas.drawText("Dirección: ${state.clientAddress}", 100f, yPosition, paint)
//            yPosition += 30f
//
//            // Lista de materiales
//            if (state.materials.isEmpty()) {
//                canvas.drawText("No se han ingresado materiales.", 100f, yPosition, paint)
//            } else {
//                canvas.drawText("Materiales:", 100f, yPosition, paint)
//                yPosition += 20f
//                state.materials.forEach { material ->
//                    canvas.drawText("- $material", 120f, yPosition, paint)
//                    yPosition += 20f
//                }
//            }
//
//            // Opciones finales (si están habilitadas)
//            if (state.hasExpiryDate) {
//                canvas.drawText("Validez: ${state.expiryDate}", 100f, yPosition, paint)
//                yPosition += 30f
//            }
//            if (state.hasPaymentMethods) {
//                canvas.drawText("Formas de Pago: ${state.paymentMethods.joinToString()}", 100f, yPosition, paint)
//                yPosition += 30f
//            }
//            if (state.hasComment) {
//                canvas.drawText("Comentario: ${state.comment}", 100f, yPosition, paint)
//            }
//
//            pdfDocument.finishPage(page)
//
//            // Configurar ContentValues para MediaStore
//            val contentValues = ContentValues().apply {
//                put(MediaStore.Downloads.DISPLAY_NAME, "presupuesto_${System.currentTimeMillis()}.pdf")
//                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
//                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
//            }
//
//            val resolver = context.contentResolver
//            // Insertar en MediaStore para obtener un URI válido
//            val uri: Uri? = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
//
//            uri?.let { outUri ->
//                resolver.openOutputStream(outUri)?.use { outputStream ->
//                    pdfDocument.writeTo(outputStream)
//                }
//            }
//
//            pdfDocument.close()
//            uri
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//}