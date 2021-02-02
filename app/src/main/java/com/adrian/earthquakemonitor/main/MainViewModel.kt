package com.adrian.earthquakemonitor.main

import android.app.Application
import androidx.lifecycle.*
import com.adrian.earthquakemonitor.Earthquake
import com.adrian.earthquakemonitor.database.getDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private var _eqList= MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
        get() = _eqList

    private val database= getDatabase(application)
    private val repository= MainRepository(database)

    init {
        viewModelScope.launch {
            _eqList.value= repository.fetchEarthquakes()
        }
    }

}