package com.example.appclima.presentacion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appclima.MainPage
import com.example.appclima.repository.modelos.Ciudad
import com.example.appclima.ui.theme.AppClimaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadesView(
    modifier: Modifier = Modifier,
    state: CiudadesEstado,
    onAction: (CiudadesIntencion) -> Unit
) {
    var value by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            // Aquí agregamos el TopAppBar
            TopAppBar(
                title = {
                    Text("Ciudades")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                value = value,
                label = { Text(text = "buscar por nombre") },
                onValueChange = {
                    value = it
                    onAction(CiudadesIntencion.Buscar(value))
                },
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(12.dp)
                    .fillMaxWidth(),
                singleLine = true
            )
            when (state) {
                CiudadesEstado.Cargando -> Text(text = "Cargando...")
                is CiudadesEstado.Error -> Text(
                    text = state.mensaje,
                    color = MaterialTheme.colorScheme.error
                )

                is CiudadesEstado.Resultado -> ListaDeCiudades(state.ciudades) {
                    onAction(
                        CiudadesIntencion.Seleccionar(it)
                    )
                }

                CiudadesEstado.Vacio -> Text(
                    text = "No se encontraron ciudades",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ListaDeCiudades(ciudades: List<Ciudad>, onSelect: (Ciudad) -> Unit) {
        LazyColumn {
            items(items = ciudades) { ciudad ->
                Card(
                    onClick = { onSelect(ciudad) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = ciudad.name,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Lat: ${ciudad.lat}, Lon: ${ciudad.lon}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "${ciudad.state.ifEmpty { ciudad.country }}",
                            style = MaterialTheme.typography.bodySmall
                        )

                    }
                }
            }
        }
    }

@Preview(showBackground = true)
@Composable
fun PreviewCiudadesView() {
    AppClimaTheme {
        CiudadesView(
            state = CiudadesEstado.Resultado(
                listOf(
                    Ciudad(
                        name = "Buenos Aires",
                        lat = 40.4168f,
                        lon = -3.7038f,
                        country = "Argentina",
                        state = ""
                    ),
                    Ciudad(
                        name = "Barcelona",
                        lat = 41.3784f,
                        lon = 2.1925f,
                        country = "España",
                        state = "Cataluña"
                    )
                )
            ),
            onAction = {}
        )
    }
}
