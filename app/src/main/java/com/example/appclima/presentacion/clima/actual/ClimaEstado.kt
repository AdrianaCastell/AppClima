package com.example.appclima.presentacion.clima.actual

sealed class ClimaEstado {
    data object Vacio : ClimaEstado()
    data object Cargando : ClimaEstado()
    data class Error(val message: String) : ClimaEstado()
    data class Resultado (
        val ciudad: String = "",
        val temperatura: Double = 0.0,
        val descripcion: String= "",
        val sensacionTermica :Double = 0.0,
    ) : ClimaEstado()
}