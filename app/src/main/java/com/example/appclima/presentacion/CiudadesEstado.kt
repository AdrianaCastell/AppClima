package com.example.appclima.presentacion

import com.example.appclima.repository.modelos.Ciudad

sealed class CiudadesEstado {
    data object Vacio: CiudadesEstado()
    data object Cargando: CiudadesEstado()
    data class Error(val mensaje: String): CiudadesEstado()
    data class Resultado( val ciudades : List<Ciudad> ) : CiudadesEstado()

}