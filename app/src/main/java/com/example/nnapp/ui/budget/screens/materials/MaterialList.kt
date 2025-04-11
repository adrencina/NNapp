package com.example.nnapp.ui.budget.screens.materials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nnapp.data.model.Material
import androidx.compose.foundation.lazy.items


@Composable
fun MaterialList(materials: List<Material>) {
    Column {
        Text(
            text = "Material    |  Cant.  |  Precio Unit.  |    Total",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = materials,
                key = { it.code } // identificador único
            ) { material ->
                MaterialItem(material)
            }
        }
    }
}