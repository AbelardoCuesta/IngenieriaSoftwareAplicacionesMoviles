package com.example.vinilosmobile.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "collectors_table")
data class Collector(
    @PrimaryKey val collectorId: Int,
    val name: String,
    val telephone: String,
    val email: String
)
