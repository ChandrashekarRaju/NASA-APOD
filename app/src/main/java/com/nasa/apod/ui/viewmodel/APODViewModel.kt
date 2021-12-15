package com.nasa.apod.ui.viewmodel

import androidx.lifecycle.*
import com.nasa.apod.data.entities.APOD
import com.nasa.apod.data.repository.APODRepository
import com.nasa.apod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class APODViewModel @Inject constructor(
    private val repository: APODRepository
) : ViewModel() {

    private val searchQuery = MutableLiveData<String>()

    val searchResult: LiveData<Resource<APOD>?> =
        searchQuery.switchMap{ date -> repository.getAPOD(date) }

    fun query(date: String) {
        searchQuery.value = date
    }

    suspend fun updateAPOD(apod:APOD) {
        repository.updateAPOD(apod)
    }
}