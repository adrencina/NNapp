package com.example.nnapp.utils.pdfutils

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun savePdfToDownloads(context: Context, pdfDocument: PdfDocument, fileName: String): File? {
    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, "$fileName.pdf")

    return try {
        FileOutputStream(file).use { outputStream ->
            pdfDocument.writeTo(outputStream)
        }
        pdfDocument.close()
        file // Retornamos el archivo guardado
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}