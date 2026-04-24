package com.valentinerutto.nightlife.data.network

data class EventDTO(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val dateTime: Long,
    val price: Double
)