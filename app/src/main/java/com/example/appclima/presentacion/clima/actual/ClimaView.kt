package com.example.appclima.presentacion.clima.actual

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.appclima.ui.theme.AppClimaTheme

@Composable
fun ClimaView(
    modifier: Modifier = Modifier,
    state: ClimaEstado,
    onAction: (ClimaIntencion) -> Unit,

) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(ClimaIntencion.actualizar)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        when (state) {
            is ClimaEstado.Cargando -> Text(text = "Cargando...")
            is ClimaEstado.Error -> Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error
            )
            is ClimaEstado.Resultado -> ResultadoView(
                ciudad = state.ciudad,
                temperatura = state.temperatura,
                descripcion = state.descripcion,
                sensacionTermica = state.sensacionTermica,

            )
            is ClimaEstado.Vacio -> Text(
                text = "No se encontraron ciudades",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ResultadoView(
    ciudad: String,
    temperatura: Double,
    descripcion: String,
    sensacionTermica: Double,
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = ciudad,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${temperatura}°",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 12.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = "Sensación térmica: ${sensacionTermica}°",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ClimaViewPreview() {
    val state = ClimaEstado.Resultado(
        ciudad = "Buenos Aires",
        temperatura = 25.0,
        descripcion = "Soleado",
        sensacionTermica = 28.0
    )
    ClimaView(
        modifier = Modifier,
        state = state,
        onAction = {},

    )
}