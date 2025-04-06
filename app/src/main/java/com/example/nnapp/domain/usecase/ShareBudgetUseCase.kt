package com.example.nnapp.domain.usecase

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import javax.inject.Inject

class ShareBudgetUseCase @Inject constructor() {
    fun sharePdf(context: Context, pdfPath: String): Intent {
        val file = File(pdfPath)
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        return Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}