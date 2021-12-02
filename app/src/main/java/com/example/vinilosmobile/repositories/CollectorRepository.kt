package com.example.vinilosmobile.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.vinilosmobile.models.Collector
import com.example.vinilosmobile.network.NetworkServiceAdapter
import com.example.vinilosmobile.database.dao.CollectorsDao

class CollectorRepository(val application: Application, collectorsDao: Any) {
        suspend fun refreshData(): List<Collector>{
            var cached = CollectorsDao.getCollectors()
            return if(cached.isNullOrEmpty()){
                val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                    emptyList()
                } else NetworkServiceAdapter.getInstance(application).getCollectors()
            } else cached
        }
}
