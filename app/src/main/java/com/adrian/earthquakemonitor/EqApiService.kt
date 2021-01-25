package com.adrian.earthquakemonitor

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


interface EqApiService {
    @GET("application.json")
    suspend fun getLastHourEarthquakes(): String
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/ ")
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

var service: EqApiService = retrofit.create<EqApiService>(EqApiService::class.java)