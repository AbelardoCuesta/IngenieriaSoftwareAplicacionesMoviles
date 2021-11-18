package com.example.vinilosmobile.repositories

import android.app.Application
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    suspend fun refreshData(): (List<Album>)  {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
       return  NetworkServiceAdapter.getInstance(application).getAlbums()
    }
}