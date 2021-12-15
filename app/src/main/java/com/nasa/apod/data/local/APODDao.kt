package com.nasa.apod.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nasa.apod.data.entities.APOD
import kotlinx.coroutines.flow.Flow

@Dao
interface APODDao {

    @Query("SELECT * FROM APOD WHERE date = :date")
    fun getAPOD(date: String): Flow<APOD>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(apod: APOD)

    @Query("SELECT * FROM APOD WHERE isFavorite = 1")
    fun getFavoriteAPODs(): LiveData<List<APOD>>
}