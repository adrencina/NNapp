package com.example.nnapp.ui.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun DatePickerComponent(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var selectedDate by remember { mutableStateOf("") }

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            selectedDate = "$d/${m + 1}/$y"
            onDateSelected(selectedDate)
        }, year, month, day
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { datePicker.show() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = if (selectedDate.isEmpty()) "Seleccionar fecha" else selectedDate)
        Button(onClick = { datePicker.show() }) {
            Text("Elegir")
        }
    }
}