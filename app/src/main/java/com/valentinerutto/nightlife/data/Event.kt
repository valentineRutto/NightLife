package com.valentinerutto.nightlife.data

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val dateTime: Long,
    val price: Double,
    val isBookmarked: Boolean = false
)

data class Booking(
    val bookingId: String,
    val eventId: String,
    val eventTitle: String,
    val ticketType: String,
    val quantity: Int,
    val totalAmountKes: Int,
    val qrCodeData: String,
    val status: BookingStatus,
    val paymentRef: String? = null,
    val createdAt: Long,
)

enum class BookingStatus { PENDING, CONFIRMED, FAILED }