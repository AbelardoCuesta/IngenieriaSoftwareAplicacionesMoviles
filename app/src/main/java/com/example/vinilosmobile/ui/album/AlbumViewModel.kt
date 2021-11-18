package com.example.vinilosmobile.ui.album


import android.app.Application
import androidx.lifecycle.*
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.repositories.AlbumRepository
import com.example.vinilosmobile.repositories.AlbumDetailRepository


class AlbumViewModel(application: Application) :  AndroidViewModel(application) {
    private val albumsRepository = AlbumRepository(application)
    private val _albums = MutableLiveData<List<Album>>()
    private val albumDetailRepository=AlbumDetailRepository(application)
    private val _album = MutableLiveData<Album>()

    val albums: LiveData<List<Album>>
        get() = _albums

    val album: LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        albumsRepository.refreshData({
            _albums.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },

            {
            _eventNetworkError.value = true
        })
        albumDetailRepository.refreshData(100,{
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

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}