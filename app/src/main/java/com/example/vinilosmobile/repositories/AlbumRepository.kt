package com.example.vinilosmobile.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.vinilosmobile.database.dao.AlbumDao
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.network.NetworkServiceAdapter

class AlbumRepository (val application: Application, private val albumDao: AlbumDao){
    suspend fun refreshData(): List<Album>{
        var cached = albumDao.getAlbums()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else NetworkServiceAdapter.getInstance(application).getAlbums()
        } else cached
    }
}