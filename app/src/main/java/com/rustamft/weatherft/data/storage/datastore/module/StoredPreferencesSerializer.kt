package com.rustamft.weatherft.data.storage.datastore.module

import androidx.datastore.core.Serializer
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.rustamft.weatherft.data.model.StoredPreferences
import java.io.InputStream
import java.io.OutputStream

object StoredPreferencesSerializer : Serializer<StoredPreferences> {

    private val gson = Gson()

    override val defaultValue: StoredPreferences
        get() = StoredPreferences()

    override suspend fun readFrom(input: InputStream): StoredPreferences {
        return try {
            val json = input.readBytes().decodeToString()
            gson.fromJson(json, StoredPreferences::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: StoredPreferences, output: OutputStream) {
        kotlin.runCatching {
            output.write(gson.toJson(t).encodeToByteArray())
        }
    }
}
