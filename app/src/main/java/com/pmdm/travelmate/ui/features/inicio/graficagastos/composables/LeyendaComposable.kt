package com.pmdm.travelmate.ui.features.inicio.graficagastos.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Square
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.travelmate.ui.theme.TravelMateTheme

@Composable
fun LeyendaComposable() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .height(60.dp)
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(20.dp)

        ) {
            Icon(
                imageVector = Icons.Filled.Square,
                contentDescription = "cuadrado",
                tint = Color(0xFF66CD54.toInt())
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                "Gasto máximo sin sobrepasar.",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

        }

        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Square,
                contentDescription = "cuadrado",
                tint = Color(0xFFFA7F5B.toInt()),
                modifier = Modifier
                    .size(20.dp)
                    .fillMaxSize()
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                "Gasto máximo sobrepasado.",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeyendaPreview() {
    TravelMateTheme {
        LeyendaComposable()
    }
}