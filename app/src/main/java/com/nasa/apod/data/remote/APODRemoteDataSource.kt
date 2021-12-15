package com.nasa.apod.data.remote

import javax.inject.Inject

class APODRemoteDataSource @Inject constructor(
    private val apodService: APODService
): BaseDataSource() {
    suspend fun getAPOD(date: String) = getResult { apodService.getAPOD(date = date) }
}