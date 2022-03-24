package com.rustamft.weatherft.util

object DegreesStringBuilder {

    fun celsius(degree: Double): String {
        var text = "$degree°C"
        if (degree > 0.0f) {
            text = "+$text"
        }
        return text
    }
}