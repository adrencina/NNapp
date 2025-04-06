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
import com.example.nnapp.ui.budget.viewmodel.BudgetViewModel
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
 *  - Opciones finales (fecha de validez, forma de pago)
 *  - Guarda en MediaStore y retorna el Uri del PDF
 */
class PdfGenerator @Inject constructor() {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun createPdf(context: Context, viewModel: BudgetViewModel): Uri? {
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

            var yPosition = margin

            // Datos estáticos de ejemplo para el encabezado
            val budgetNumber = "PR: 0000-00039132"
            val budgetDate = "Fecha: 23/04/2024"

            // 1) Logo y/o nombre de empresa (Placeholder)
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
            canvas.drawText("Cliente: ${viewModel.clientName.value}", margin, yPosition, paint)
            yPosition += 18f
            canvas.drawText("Dirección: ${viewModel.address.value}", margin, yPosition, paint)
            yPosition += 18f
            canvas.drawText("DNI: ${viewModel.dni.value}", margin, yPosition, paint)
            yPosition += 18f
            canvas.drawText("Teléfono: ${viewModel.phone.value}", margin, yPosition, paint)
            yPosition += 20f

            // 5) Sección DETALLE
            paint.isFakeBoldText = true
            canvas.drawText("DETALLE", margin, yPosition, paint)
            yPosition += 20f
            paint.isFakeBoldText = false

            // 6) Lista de materiales
            if (viewModel.materials.value.isEmpty()) {
                canvas.drawText("No se han ingresado materiales.", margin, yPosition, paint)
                yPosition += 20f
            } else {
                canvas.drawText("Materiales:", margin, yPosition, paint)
                yPosition += 20f

                viewModel.materials.value.forEach { material ->
                    // Formateamos el detalle de cada material
                    val materialText = "- ${material.name}: ${material.quantity} x \$${material.unitPrice} = \$${material.totalPrice}"
                    canvas.drawText(materialText, margin + 20f, yPosition, paint)
                    yPosition += 18f
                }
            }

            // 7) Opciones finales: forma de pago y fecha de validez
            if (viewModel.offerValidityDate.value.isNotBlank()) {
                yPosition += 20f
                canvas.drawText("Validez: ${viewModel.offerValidityDate.value}", margin, yPosition, paint)
                yPosition += 18f
            }
            if (viewModel.paymentMethod.value.isNotBlank()) {
                yPosition += 20f
                canvas.drawText("Forma de Pago: ${viewModel.paymentMethod.value}", margin, yPosition, paint)
                yPosition += 18f
            }

            // Finalizar la página
            pdfDocument.finishPage(page)

            // Guardar en MediaStore (Downloads)
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