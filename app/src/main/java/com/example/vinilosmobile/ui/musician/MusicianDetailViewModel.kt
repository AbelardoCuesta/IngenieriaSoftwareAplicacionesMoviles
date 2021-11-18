package com.example.vinilosmobile.ui.musician

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilosmobile.models.Musician
import com.example.vinilosmobile.repositories.MusicianDetailRepository

class MusicianDetailViewModel(application: Application, musicianId: Int) :  AndroidViewModel(application) {
    private val musicianDetailRepository= MusicianDetailRepository(application)
    private val _musician = MutableLiveData<Musician>()


    val musician: LiveData<Musician>
        get() = _musician

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val id:Int = musicianId
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        musicianDetailRepository.refreshData(id,{
            _musician.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false

        },  {
            _eventNetworkError.value = true
        })

    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val musicianId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusicianDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MusicianDetailViewModel(app, musicianId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}