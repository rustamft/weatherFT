package com.rustamft.weatherft.data.storage

import com.rustamft.weatherft.data.model.CityData
import kotlinx.coroutines.flow.Flow

internal interface CityStorage {

    suspend fun save(cityData: CityData)
    fun get(): Flow<CityData>
}
