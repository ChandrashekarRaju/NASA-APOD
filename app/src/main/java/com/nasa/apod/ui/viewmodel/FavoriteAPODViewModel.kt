package com.nasa.apod.ui.viewmodel

import androidx.lifecycle.*
import com.nasa.apod.data.entities.APOD
import com.nasa.apod.data.repository.APODRepository
import com.nasa.apod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteAPODViewModel @Inject constructor(
    private val repository: APODRepository
) : ViewModel() {

    fun favoriteAPOD(): LiveData<List<APOD>> {
        return repository.favoriteAPOD()
    }
}