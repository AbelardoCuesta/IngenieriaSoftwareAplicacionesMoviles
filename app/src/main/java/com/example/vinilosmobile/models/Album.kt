package com.example.vinilosmobile.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "album_table")

data class Album(
    @PrimaryKey
    val albumId: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String
)
