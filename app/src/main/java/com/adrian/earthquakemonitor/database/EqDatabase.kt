package com.adrian.earthquakemonitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adrian.earthquakemonitor.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EqDatabase: RoomDatabase() {
    abstract  val eqDao: EqDao
}

//código genérico que se utiliza para acceder a la base de datos, pero es muy repetitivo entre proyectos donde se implementa Room
//cópialo, pégalo y adáptalo
private  lateinit var INSTANCE: EqDatabase

fun getDatabase(context: Context): EqDatabase{
    synchronized(EqDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE= Room.databaseBuilder(
                context.applicationContext,
                EqDatabase::class.java,
                "eartquake_db"
            ).build()
        }
        return INSTANCE
    }

}