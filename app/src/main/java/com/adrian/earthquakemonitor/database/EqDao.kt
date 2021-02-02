package com.adrian.earthquakemonitor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adrian.earthquakemonitor.Earthquake

@Dao
interface EqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertaLL(eqLlist: MutableList<Earthquake>)

    @Query("SELECT * FROM earthquakes")
    fun getEarthquakes(): MutableList<Earthquake>
}