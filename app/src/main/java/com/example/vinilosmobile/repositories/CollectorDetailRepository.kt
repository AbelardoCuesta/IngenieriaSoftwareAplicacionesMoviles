package com.example.vinilosmobile.repositories

import android.app.Application
import com.example.vinilosmobile.models.Collector
import com.example.vinilosmobile.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CollectorDetailRepository (val application: Application) {
    val format = Json { }
    suspend fun refreshData(collectorId: Int): Collector? {
        var collector = getCollectorDetail(collectorId)
        return if (collector == null) {
            collector = NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
            addCollectorDetail(collectorId, collector)
            collector
        } else {
            collector
        }
    }

    suspend fun getCollectorDetail(collectorId: Int): Collector? {
        val format = Json { }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.COLLECTORS_SPREFS)
        if (prefs.contains(collectorId.toString())) {
            val storedVal = prefs.getString(collectorId.toString(), "")
            if (!storedVal.isNullOrBlank()) {
                return format.decodeFromString<Collector>(storedVal)
            }
        }
        return null
    }

    suspend fun addCollectorDetail(collectorId: Int, collector: Collector) {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.COLLECTORS_SPREFS)
        if (!prefs.contains(collectorId.toString())) {
            var store = format.encodeToString(collector)
            with(prefs.edit(), {
                putString(collectorId.toString(), store)
                apply()
            })
        }
    }

}