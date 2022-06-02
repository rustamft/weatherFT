package com.rustamft.weatherft.data.model.container

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.model.AppPreferencesData
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.WeatherData
import java.io.InputStream
import java.io.OutputStream

internal data class DataContainer(
    val apiKeyData: ApiKeyData = ApiKeyData(),
    val cityData: CityData = CityData(),
    val weatherData: WeatherData = WeatherData(),
    val appPreferencesData: AppPreferencesData = AppPreferencesData()
) {

    companion object Serializer : androidx.datastore.core.Serializer<DataContainer> {

        private val gson = Gson()

        override val defaultValue: DataContainer
            get() = DataContainer()

        override suspend fun readFrom(input: InputStream): DataContainer {
            return try {
                val json = input.readBytes().decodeToString()
                gson.fromJson(json, DataContainer::class.java)
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
                defaultValue
            }
        }

        override suspend fun writeTo(t: DataContainer, output: OutputStream) {
            kotlin.runCatching {
                output.write(gson.toJson(t).encodeToByteArray())
            }
        }
    }
}
