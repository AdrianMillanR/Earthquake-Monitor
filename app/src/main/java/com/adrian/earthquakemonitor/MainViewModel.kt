package com.adrian.earthquakemonitor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainViewModel: ViewModel() {
    private var _eqList= MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
        get() = _eqList

    init {
        viewModelScope.launch {
            _eqList.value= fetchEarthquakes()
        }
    }

    private suspend fun fetchEarthquakes(): MutableList<Earthquake> {
        return withContext(Dispatchers.IO){
            val eqJsonResponse= service.getLastHourEarthquakes()
            val eqList=parseEqResult(eqJsonResponse)

            eqList
        }


    }

    private fun parseEqResult(eqJsonResponse:EqJsonResponse): MutableList<Earthquake>{
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