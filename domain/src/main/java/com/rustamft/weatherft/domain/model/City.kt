package com.rustamft.weatherft.domain.model

data class City(
    val name: String = "",
    var localNames: Map<String, String> = emptyMap(),
    val country: String = "",
    val state: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
)
