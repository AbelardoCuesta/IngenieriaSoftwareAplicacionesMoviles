package com.example.vinilosmobile.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosmobile.models.Musician
import com.example.vinilosmobile.network.NetworkServiceAdapter

class MusicianDetailRepository (val application: Application) {
    fun refreshData(musicianId: Int, callback: (Musician) -> Unit, onError: (VolleyError) -> Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getMusician(musicianId,
            {
                callback(it)
            },
            onError
        )
    }
}