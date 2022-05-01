package com.rustamft.weatherft.data.model

internal data class CityData(
    val name: String = "",
    var local_names: LocalNames = LocalNames(),
    val country: String = "",
    val state: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
) {

    data class LocalNames(
        val ascii: String = "",
        val feature_name: String = "",
        val ar: String = "",
        val be: String = "",
        val ca: String = "",
        val cs: String = "",
        val cv: String = "",
        val de: String = "",
        val en: String = "",
        val eo: String = "",
        val es: String = "",
        val et: String = "",
        val fi: String = "",
        val fr: String = "",
        val hi: String = "",
        val hr: String = "",
        val hu: String = "",
        val `is`: String = "",
        val ja: String = "",
        val kn: String = "",
        val ko: String = "",
        val ku: String = "",
        val lt: String = "",
        val nl: String = "",
        val oc: String = "",
        val pl: String = "",
        val pt: String = "",
        val ro: String = "",
        val ru: String = "",
        val sk: String = "",
        val sl: String = "",
        val uk: String = "",
        val zh: String = ""
    )
}
