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
            val eqListString= service.getLastHourEarthquakes()
            val eqList=parseEqResult(eqListString)

            eqList
        }


    }

    private fun parseEqResult(eqListString:String): MutableList<Earthquake>{
        val eqList= mutableListOf<Earthquake>()
        val eqJsonObject= JSONObject(eqListString)
        val featuresJsonArray=eqJsonObject.getJSONArray("features")

        for (i in 0 until featuresJsonArray.length()){
            val featuresJsonObject=featuresJsonArray[i] as JSONObject
            val id = featuresJsonObject.getString("id")

            val propertiesJsonObject= featuresJsonObject.getJSONObject("properties")
            val place= propertiesJsonObject.getString("place")
            val magnitude=propertiesJsonObject.getDouble("mag")
            val time= propertiesJsonObject.getLong("time")

            val geometryJsonObject= featuresJsonObject.getJSONObject("geometry")
            val coordinatesJsonArray= geometryJsonObject.getJSONArray("coordinates")
            val longitude= coordinatesJsonArray.getDouble(0)
            val latitude= coordinatesJsonArray.getDouble(1)

            val earthquake = Earthquake(id,place,magnitude,time,longitude,latitude)
            eqList.add(earthquake)
        }

        return eqList

    }

}