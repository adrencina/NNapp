package com.example.nnapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    keyboardOptions: KeyboardOptions
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it.trimStart()) },
            label = { Text(label) },
            isError = error != null,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )
        if (error != null) {
            Text(text = error, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
    }
}