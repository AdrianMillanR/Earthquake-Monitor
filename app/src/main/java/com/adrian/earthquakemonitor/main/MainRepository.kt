package com.adrian.earthquakemonitor.main

import androidx.lifecycle.LiveData
import com.adrian.earthquakemonitor.Earthquake
import com.adrian.earthquakemonitor.api.EqJsonResponse
import com.adrian.earthquakemonitor.api.service
import com.adrian.earthquakemonitor.database.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: EqDatabase) {

    val eqList: LiveData<MutableList<Earthquake>> =database.eqDao.getEarthquakes()

    suspend fun fetchEarthquakes(){
        return withContext(Dispatchers.IO){
            val eqJsonResponse= service.getLastHourEarthquakes()
            val eqList=parseEqResult(eqJsonResponse)

            database.eqDao.insertaLL(eqList)
        }


    }

    private fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake>{
        val eqList= mutableListOf<Earthquake>()
        val featureList= eqJsonResponse.features
        for(feature in featureList){
            val properties= feature.properties
            val id= feature.id

            val magnitude= properties.mag
            val place= properties.place
            val time= properties.time

            val geometry= feature.geometry
            val latitude= geometry.latitude
            val longitude= geometry.longitude

            eqList.add(Earthquake(id,place, magnitude, time, longitude, latitude))
        }


        return eqList

    }
}