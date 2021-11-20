package com.example.vinilosmobile.repositories
import android.app.Application
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.network.NetworkServiceAdapter
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import android.content.Context
import android.net.ConnectivityManager


class AlbumDetailRepository(val application: Application) {
    val format = Json { }
    suspend fun refreshData(albumId: Int): Album? {
        var album = getAlbumDetail(albumId)
        return if (album == null) {
            album = NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
            addAlbumDetail(albumId, album)
            album
        } else {
            album
        }
    }


    suspend fun getAlbumDetail(albumId: Int): Album? {
        val format = Json { }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if (prefs.contains(albumId.toString())) {
            val storedVal = prefs.getString(albumId.toString(), "")
            if (!storedVal.isNullOrBlank()) {
                return format.decodeFromString<Album>(storedVal)
            }
        }
        return null
    }

    suspend fun addAlbumDetail(albumId: Int, album: Album) {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if (!prefs.contains(albumId.toString())) {
            var store = format.encodeToString(album)
            with(prefs.edit(), {
                putString(albumId.toString(), store)
                apply()
            })
        }
    }

}