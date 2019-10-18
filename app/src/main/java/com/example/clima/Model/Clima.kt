package com.example.clima.Model

data class Clima(
    var lugar: Lugar? = null,
    var datosIcon: String? = null,
    var condicionActual: CondicionActual = CondicionActual(),
    var temperatura: Temperatura = Temperatura(),
    var viento: Viento = Viento(),
    var nieve: Nieve = Nieve(),
    var nubes: Nubes = Nubes()
)