package com.example.vinilosmobile.repositories

import android.app.Application
import com.example.vinilosmobile.models.Collector
import com.example.vinilosmobile.network.NetworkServiceAdapter

class CollectorRepository(val application: Application) {

    suspend fun refreshData(): List<Collector>{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }
}