package com.valentinerutto.nightlife.data.network

import androidx.collection.IntSet

data class EventDTO(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val dateTime: Long,
    val price: Double,
    val isSoldOut: Boolean = false,
    val category: String
)
