package com.example.appclima.repository

import com.example.appclima.repository.modelos.Ciudad

interface Repositorio {
    suspend fun buscarCiudad(ciudad: String): List<Ciudad>

}