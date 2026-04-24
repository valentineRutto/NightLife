package com.valentinerutto.nightlife.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valentinerutto.nightlife.data.Event

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val dateTime: Long,
    val price: Double,
    val isSoldOut: Boolean = false,
    val category: String
)

fun EventEntity.toDomain() = Event(
    id, title, description, imageUrl, location, dateTime, price,isSoldOut,category
)