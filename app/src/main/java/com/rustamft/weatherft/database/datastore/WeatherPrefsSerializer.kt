package com.rustamft.weatherft.database.datastore

import androidx.datastore.core.Serializer
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.InputStream
import java.io.OutputStream

object WeatherPrefsSerializer : Serializer<WeatherPrefs> {

    private val gson = Gson()

    override val defaultValue: WeatherPrefs
        get() = WeatherPrefs()

    override suspend fun readFrom(input: InputStream): WeatherPrefs {
        return try {
            val json = input.readBytes().decodeToString()
            gson.fromJson(json, WeatherPrefs::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: WeatherPrefs, output: OutputStream) {
        kotlin.runCatching {
            output.write(gson.toJson(t).encodeToByteArray())
        }
    }
}
