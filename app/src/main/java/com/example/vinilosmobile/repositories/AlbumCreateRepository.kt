package com.example.vinilosmobile.repositories

import android.app.Application
import com.example.vinilosmobile.network.NetworkServiceAdapter
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject


class AlbumCreateRepository(val application: Application) {
    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    suspend fun refreshData() {
    }


    suspend fun postAlbum(name: String, cover: String, genre: String, recordLabel: String, albumDate: String, description: String) {
        val rootObject = JSONObject()
        rootObject.put("name", name)
        rootObject.put("cover", cover)
        rootObject.put("releaseDate",albumDate)
        rootObject.put("description",description)
        rootObject.put("genre", genre)
        rootObject.put("recordLabel", recordLabel)
        NetworkServiceAdapter.getInstance(application).postAlbum(rootObject, {
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }
}