package com.example.vinilosmobile.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosmobile.models.Collector
import com.example.vinilosmobile.network.NetworkServiceAdapter

class CollectorRepository(val application: Application) {

    fun refreshData(callback: (List<Collector>) -> Unit, onError: (VolleyError) -> Unit) {

        NetworkServiceAdapter.getInstance(application).getCollectors(
            {
                callback(it)
            },
            onError
        )
    }
}