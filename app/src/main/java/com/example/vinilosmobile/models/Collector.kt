package com.example.vinilosmobile.models
import kotlinx.serialization.Serializable

@Serializable
data class Collector(
    val collectorId:Int,
    val name:String,
    val telephone:String,
    val email:String
)
