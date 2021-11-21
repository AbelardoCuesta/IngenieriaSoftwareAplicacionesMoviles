package com.example.vinilosmobile.models
import kotlinx.serialization.Serializable

@Serializable
data class Musician(
    val musicianId: Int,
    val name:String,
    val image: String,
    val description: String,
    val birthDate: String

)
