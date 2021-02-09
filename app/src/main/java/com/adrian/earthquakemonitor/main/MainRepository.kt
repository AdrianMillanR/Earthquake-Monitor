package com.adrian.earthquakemonitor.main

import androidx.lifecycle.LiveData
import com.adrian.earthquakemonitor.Earthquake
import com.adrian.earthquakemonitor.api.EqJsonResponse
import com.adrian.earthquakemonitor.api.service
import com.adrian.earthquakemonitor.database.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: EqDatabase) {

    suspend fun fetchEarthquakes(sortByMagnitude:Boolean): MutableList<Earthquake>{
        return withContext(Dispatchers.IO){
            val eqJsonResponse= service.getLastHourEarthquakes()
            val eqList=parseEqResult(eqJsonResponse)

            database.eqDao.insertaLL(eqList)
            fetchEartquakesFromDb(sortByMagnitude)
        }
    }

    suspend fun fetchEartquakesFromDb(sortByMagnitude:Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            if (sortByMagnitude) {
                database.eqDao.getEarthquakesByMagnitude()
            } else {
                database.eqDao.getEarthquakes()
            }
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