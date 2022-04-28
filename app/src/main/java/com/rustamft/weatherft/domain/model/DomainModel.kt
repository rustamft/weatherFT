package com.rustamft.weatherft.domain.model

import com.google.gson.Gson

abstract class DomainModel {

    fun <T> convertTo(model: Class<T>): T {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, model)
    }
}
