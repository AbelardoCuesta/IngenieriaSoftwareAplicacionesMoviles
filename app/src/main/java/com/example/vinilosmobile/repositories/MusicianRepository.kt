package com.example.vinilosmobile.repositories

import android.app.Application
import com.example.vinilosmobile.models.Musician
import com.example.vinilosmobile.network.NetworkServiceAdapter

class MusicianRepository(val application: Application) {
    suspend fun refreshData(): (List<Musician>){
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
       return  NetworkServiceAdapter.getInstance(application).getMusicians()
    }
}