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
     private val _status= MutableLiveData<ApiResponseStatus>()
    val status : LiveData<ApiResponseStatus>
        get()= _status

    val eqList = repository.eqList

    init {
        viewModelScope.launch {
            try {
                _status.value=ApiResponseStatus.LOADING
                repository.fetchEarthquakes()
                _status.value=ApiResponseStatus.DONE
            } catch (e: UnknownHostException){
                _status.value=ApiResponseStatus.NOT_INTERNETCONECTION
                Log.d(TAG,"No internet conection.", e)
            }

        }
    }

}