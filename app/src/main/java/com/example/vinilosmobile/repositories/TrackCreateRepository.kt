package com.example.vinilosmobile.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.vinilosmobile.network.NetworkServiceAdapter
import org.json.JSONObject

class TrackCreateRepository (val application: Application) {
    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    suspend fun refreshData() {
    }

    suspend fun postTrack(name: String, duration: String, albumId: Int) {
        val rootObject = JSONObject()
        rootObject.put("name", name)
        rootObject.put("duration", duration)
        NetworkServiceAdapter.getInstance(application).postTrack(rootObject,albumId, {
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }
}