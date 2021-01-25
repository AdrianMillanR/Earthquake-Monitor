package com.adrian.earthquakemonitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val eqList= mutableListOf<Earthquake>()
            eqList.add(Earthquake("1","Ciudad de México",4.3,273847152L,-102.4756,28.4748))
            eqList.add(Earthquake("2","Buenos Aires",1.8,273847152L,-102.4756,28.4748))
            eqList.add(Earthquake("3","Madrid",3.6,273847152L,-102.4756,28.4748))
            eqList.add(Earthquake("4","Uta",5.6,273847152L,-102.4756,28.4748))
            eqList.add(Earthquake("5","Roma",1.2,273847152L,-102.4756,28.4748))
            eqList.add(Earthquake("6","Tokio",7.3,273847152L,-102.4756,28.4748))
            eqList
        }


    }

}