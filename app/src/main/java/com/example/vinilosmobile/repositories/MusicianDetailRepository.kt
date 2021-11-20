package com.example.vinilosmobile.repositories

import android.app.Application
import com.example.vinilosmobile.models.Musician
import com.example.vinilosmobile.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MusicianDetailRepository (val application: Application) {

    suspend fun refreshData(musicianId: Int): Musician? {

        var musician = getMusicianDetail(musicianId)
        return if (musician == null) {
            musician = NetworkServiceAdapter.getInstance(application).getMusician(musicianId)
            addMusicianDetail(musicianId, musician)
            musician
        } else {
            musician
        }
    }

    suspend fun getMusicianDetail(musicianId: Int): Musician? {
        val format = Json { }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.MUSICIANS_SPREFS)
        if (prefs.contains(musicianId.toString())) {
            val storedVal = prefs.getString(musicianId.toString(), "")
            if (!storedVal.isNullOrBlank()) {
                return format.decodeFromString<Musician>(storedVal)
            }
        }
        return null
    }

    suspend fun addMusicianDetail(musicianId: Int, musician: Musician) {
        val format = Json { }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.MUSICIANS_SPREFS)
        if (!prefs.contains(musicianId.toString())) {
            var store = format.encodeToString(musician)
            with(prefs.edit(), {
                putString(musicianId.toString(), store)
                apply()
            })
        }
    }

}