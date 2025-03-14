package com.example.nnapp.utils.pdfutils

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Función para compartir un archivo PDF mediante un Intent de Android.
 * Recibe directamente un Uri.
 *
 * @param context Contexto de la aplicación.
 * @param uri Uri del archivo PDF que se quiere compartir.
 */
fun sharePdf(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(intent, "Compartir PDF"))
}