package com.adrian.earthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrian.earthquakemonitor.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EQ_PLACE= "eartquake_place"
        const val EQ_MAGNITUDE= "eartquake_magnitude"
        const val EQ_LONGITUDE= "eartquake_longitude"
        const val EQ_LATITUDE= "eartquake_latitude"
        const val EQ_TIME= "eartquake_time"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle= intent.extras!!
        val eqPlace=bundle.getString(EQ_PLACE) ?: ""
        val eqMagnitude=bundle.getDouble(EQ_MAGNITUDE) ?: ""
        val eqLongitude=bundle.getDouble(EQ_LONGITUDE) ?: ""
        val eqLatitude=bundle.getDouble(EQ_LATITUDE) ?: ""
        val eqTime=bundle.getLong(EQ_TIME) ?: ""



        binding.eartquakePlace.text= eqPlace
        binding.eartquakeMag.text=eqMagnitude.toString()
        binding.earthquakeLatitude.text=eqLatitude.toString()
        binding.earthquakeLongitude.text=eqLongitude.toString()
        binding.eartquakeDate.text=eqTime.toString()
    }
}