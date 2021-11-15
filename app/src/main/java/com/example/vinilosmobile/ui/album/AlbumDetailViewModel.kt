package com.example.vinilosmobile.ui.album

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.repositories.AlbumDetailRepository

class AlbumDetailViewModel(application: Application, albumId: Int) :  AndroidViewModel(application) {
    private val albumDetailRepository= AlbumDetailRepository(application)
    private val _album = MutableLiveData<Album>()


    val album: LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val id:Int = albumId
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        albumDetailRepository.refreshData(id,{
            _album.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false

        },  {
            _eventNetworkError.value = true
        })

    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application,val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetailViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}