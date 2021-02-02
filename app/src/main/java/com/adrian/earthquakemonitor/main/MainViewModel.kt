package com.adrian.earthquakemonitor.main

import android.app.Application
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.*
import com.adrian.earthquakemonitor.Earthquake
import com.adrian.earthquakemonitor.database.getDatabase
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private  val TAG = MainViewModel::class.java.simpleName
class MainViewModel(application: Application): AndroidViewModel(application) {
    private val database= getDatabase(application)
    private val repository= MainRepository(database)

    val eqList = repository.eqList

    init {
        viewModelScope.launch {
            try {
                repository.fetchEarthquakes()
            } catch (e: UnknownHostException){
                Log.d(TAG,"No internet conection.", e)
            }

        }
    }

}