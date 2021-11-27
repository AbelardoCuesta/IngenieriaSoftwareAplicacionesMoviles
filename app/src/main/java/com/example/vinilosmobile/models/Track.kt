package com.example.vinilosmobile.models
import kotlinx.serialization.Serializable

@Serializable
data class Track (
    val name: String,
    val duration: String
    )