package com.example.appclima.presentacion.clima.pronostico
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.appclima.repository.Repositorio
import com.example.appclima.router.Router
import kotlinx.coroutines.launch


class PronosticoViewModel(
    val respositorio: Repositorio,
    val router: Router,
    val nombre: String
) : ViewModel() {

    var uiState by mutableStateOf<PronosticoEstado>(PronosticoEstado.Vacio)

    fun ejecutar(intencion: PronosticoIntencion){
        when(intencion){
            PronosticoIntencion.actualizar -> obtenerPronostico()
        }
    }

    fun obtenerPronostico() {
        uiState = PronosticoEstado.Cargando
        viewModelScope.launch {
            try{
                val forecast = respositorio.obtenerPronostico(nombre).filter {
                    true
                }
                uiState = PronosticoEstado.Resultado(forecast)
            } catch (exception: Exception){
                uiState = PronosticoEstado.Error(exception.localizedMessage ?: "error")
            }
        }
    }

}

class PronosticoViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router,
    private val nombre: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PronosticoViewModel::class.java)) {
            return PronosticoViewModel(repositorio,router,nombre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}