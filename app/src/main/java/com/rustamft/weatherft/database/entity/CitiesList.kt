package com.rustamft.weatherft.database.entity

class CitiesList : ArrayList<City>()

data class City(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)

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