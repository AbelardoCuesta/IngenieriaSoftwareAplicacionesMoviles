package com.example.vinilosmobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vinilosmobile.models.Album

@Dao
interface AlbumDao {
    @Query("SELECT * FROM album_table")
    fun getAlbums():List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Query("DELETE FROM album_table")
    suspend fun deleteAll():Int
}