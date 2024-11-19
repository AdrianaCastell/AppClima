package com.example.appclima.presentacion.clima.actual

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.appclima.repository.Repositorio
import com.example.appclima.router.Router
import kotlinx.coroutines.launch

class ClimaViewModel(
    val respositorio: Repositorio,
    val router: Router,
    val lat : Float,
    val lon : Float,
    val nombre: String
) : ViewModel() {

    var uiState by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)

    fun ejecutar(intencion: ClimaIntencion){
        when(intencion){
            ClimaIntencion.actualizar -> obtenerClima()
        }
    }

    fun obtenerClima() {
        uiState = ClimaEstado.Cargando
        viewModelScope.launch {
            try{
                val clima = respositorio.obtenerClima(lat = lat, lon = lon)
                uiState = ClimaEstado.Resultado(
                    ciudad = clima.name ,
                    temperatura = clima.main.temp,
                    descripcion = clima.weather.first().description,
                    sensacionTermica = clima.main.feels_like,
                )
            } catch (exception: Exception){
                uiState = ClimaEstado.Error(exception.localizedMessage ?: "error")
            }
        }
    }

}

class ClimaViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router,
    private val lat: Float,
    private val lon: Float,
    private val nombre: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClimaViewModel::class.java)) {
            return ClimaViewModel(repositorio,router,lat,lon,nombre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}