package com.example.appclima.repository
import com.example.appclima.repository.modelos.Ciudad
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiRepositorio : Repositorio {

    private val apiKey = "b15c1e3cb008727ca49028b6e387abeb"

    private val cliente = HttpClient(){
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun buscarCiudad(ciudad: String): List<Ciudad> {
        val respuesta = cliente.get("https://api.openweathermap.org/geo/1.0/direct"){
            parameter("q",ciudad)
            parameter("limit",100)
            parameter("appid",apiKey)
        }

        if (respuesta.status == HttpStatusCode.OK){
            val ciudades = respuesta.body<List<Ciudad>>()
            return ciudades
        }else{
            throw Exception()
        }
    }
}