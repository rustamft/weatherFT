package com.rustamft.weatherft.domain.util

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
