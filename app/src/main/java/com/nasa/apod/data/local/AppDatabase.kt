package com.nasa.apod.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nasa.apod.data.entities.APOD

@Database(entities = [APOD::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun apodDao(): APODDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "NASA_db")
                .fallbackToDestructiveMigration()
                .build()
    }

}