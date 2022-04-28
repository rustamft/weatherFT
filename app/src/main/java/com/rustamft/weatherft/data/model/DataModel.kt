package com.rustamft.weatherft.data.model

import com.google.gson.Gson

abstract class DataModel {

    fun <T> convertTo(model: Class<T>): T {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, model)
    }
}
