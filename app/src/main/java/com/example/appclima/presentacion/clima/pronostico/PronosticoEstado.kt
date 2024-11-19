package com.example.appclima.presentacion.clima.pronostico


import com.example.appclima.repository.modelos.ListForecast

sealed class PronosticoEstado {
    data class Error(val mensaje :String = "", ) : PronosticoEstado()
    data object Vacio: PronosticoEstado()
    data object Cargando: PronosticoEstado()
    data class Resultado(val pronosticos: List<ListForecast>,) : PronosticoEstado()

}
