package com.nasa.apod.data.remote

import com.nasa.apod.data.entities.APOD
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APODService {

    @GET("/planetary/apod")
    suspend fun getAPOD(
        @Query("api_key") apiKey: String = "ubZ4ewoKq3w2qOCbt7gGlbE29pBnb2JjxDrASCaL",
        @Query("date") date: String
    ): Response<APOD>
}