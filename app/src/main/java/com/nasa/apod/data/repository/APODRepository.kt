package com.nasa.apod.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.nasa.apod.data.entities.APOD
import com.nasa.apod.data.local.APODDao
import com.nasa.apod.data.remote.APODRemoteDataSource
import com.nasa.apod.utils.networkBoundResource
import javax.inject.Inject

class APODRepository @Inject constructor(
    private val remoteDataSource: APODRemoteDataSource,
    private val localDataSource: APODDao
) {

    fun getAPOD(date: String) = networkBoundResource(
        query = {
            localDataSource.getAPOD(date = date)
        },
        fetch = {
            remoteDataSource.getAPOD(date = date)
        },
        saveFetchResult = { result ->
            result.data?.let { localDataSource.insert(it) }
        },
        shouldFetch = { result ->
            result == null
        }
    ).asLiveData()

    suspend fun updateAPOD(apod:APOD) {
        localDataSource.insert(apod)
    }

    fun favoriteAPOD(): LiveData<List<APOD>> {
        return localDataSource.getFavoriteAPODs()
    }
}