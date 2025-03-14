package com.example.nnapp.ui.components

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PdfPreviewDialog(onDismiss: () -> Unit, pdfUri: Uri?) {
    if (pdfUri == null) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Vista previa del presupuesto") },
        text = {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        settings.allowFileAccess = true
                        loadUrl(pdfUri.toString())
                    }
                }
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}