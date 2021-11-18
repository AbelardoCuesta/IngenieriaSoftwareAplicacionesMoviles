package com.example.vinilosmobile.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.models.Musician
import com.example.vinilosmobile.network.NetworkServiceAdapter

class MusicianDetailRepository (val application: Application) {

    suspend fun refreshData(musicianId: Int): (Musician)  {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return  NetworkServiceAdapter.getInstance(application).getMusician(musicianId)
    }

}