package com.example.nnapp.domain.usecase

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nnapp.ui.budget.viewmodel.BudgetViewModel
import com.example.nnapp.utils.pdfutils.PdfGenerator
import javax.inject.Inject

class GeneratePdfUseCase @Inject constructor(
    private val pdfGenerator: PdfGenerator
) {
    @RequiresApi(Build.VERSION_CODES.Q)
    suspend operator fun invoke(context: Context, viewModel: BudgetViewModel): Uri? {
        return pdfGenerator.createPdf(context, viewModel)
    }
}