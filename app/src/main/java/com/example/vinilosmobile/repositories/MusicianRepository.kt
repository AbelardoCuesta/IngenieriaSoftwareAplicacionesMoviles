package com.example.vinilosmobile.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosmobile.models.Musician
import com.example.vinilosmobile.network.NetworkServiceAdapter

class MusicianRepository(val application: Application) {
    fun refreshData(callback: (List<Musician>) -> Unit, onError: (VolleyError) -> Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getMusicians(
            {
                //Guardar los registros de la variable it en un almacén de datos local para uso futuro
                callback(it)
            },
            onError
        )
    }
}