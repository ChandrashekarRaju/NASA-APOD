package com.nasa.apod.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nasa.apod.data.local.APODDao
import com.nasa.apod.data.local.AppDatabase
import com.nasa.apod.data.remote.APODRemoteDataSource
import com.nasa.apod.data.remote.APODService
import com.nasa.apod.data.repository.APODRepository
import com.nasa.apod.ui.adapter.APODListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideAPODService(retrofit: Retrofit): APODService = retrofit.create(APODService::class.java)

    @Singleton
    @Provides
    fun provideAPODRemoteDataSource(apodService: APODService) = APODRemoteDataSource(apodService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideAPODDao(db: AppDatabase) = db.apodDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: APODRemoteDataSource,
                          localDataSource: APODDao) =
        APODRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideAPODListAdapter(): APODListAdapter {
        return APODListAdapter()
    }
}