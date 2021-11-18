package com.example.vinilosmobile.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.network.NetworkServiceAdapter


class AlbumDetailRepository(val application: Application) {

    suspend fun refreshData(albumId: Int): (Album)  {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return  NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
    }
}