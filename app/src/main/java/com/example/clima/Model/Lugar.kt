package com.example.clima.Model

data class Lugar(
    var longitud: Float = 0.toFloat(),
    var latitud: Float = 0.toFloat(),
    var amanecer: Long = 0,
    var anochecer: Long = 0,
    var pais: String? = null,
    var ciudad: String? = null,
    var actualizacion: Long = 0
)