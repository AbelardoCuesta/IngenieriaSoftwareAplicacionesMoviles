package com.example.vinilosmobile.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.network.NetworkServiceAdapter


class AlbumDetailRepository(val application: Application) {
    fun refreshData(albumId: Int,callback: (Album) -> Unit, onError: (VolleyError) -> Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getAlbum(albumId,
            {
                //Guardar los albumes de la variable it en un almacén de datos local para uso futuro
                callback(it)
            },
            onError
        )
    }
}