package com.rustamft.weatherft.database.entity

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CitiesList : ArrayList<City>()

data class City(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
) {

    fun getLocalName(language: String): String {
        val map = local_names.serializeToMap()
        return map.getValue(language)
    }
}

data class LocalNames(
    val ar: String,
    val ascii: String,
    val be: String,
    val ca: String,
    val cs: String,
    val cv: String,
    val de: String,
    val en: String,
    val eo: String,
    val es: String,
    val et: String,
    val feature_name: String,
    val fi: String,
    val fr: String,
    val hi: String,
    val hr: String,
    val hu: String,
    val `is`: String,
    val ja: String,
    val kn: String,
    val ko: String,
    val ku: String,
    val lt: String,
    val nl: String,
    val oc: String,
    val pl: String,
    val pt: String,
    val ro: String,
    val ru: String,
    val sk: String,
    val sl: String,
    val uk: String,
    val zh: String
)

fun <T> T.serializeToMap(): Map<String, String> {
    return convert()
}

inline fun <I, reified O> I.convert(): O {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}