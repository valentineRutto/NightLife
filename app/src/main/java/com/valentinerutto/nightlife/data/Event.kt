package com.valentinerutto.nightlife.data

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val dateTime: Long,
    val price: Double,
    val isSoldOut: Boolean = false,val category: String
)

sealed class BookingStep {
    object Details : BookingStep()
    object Form : BookingStep()
    object Confirm : BookingStep()
    object Success : BookingStep()
}

data class BookingUiState(
    val step: BookingStep = BookingStep.Details,
    val selectedTicket: TicketType = TicketType.Regular,
    val quantity: Int = 1,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val bookingRef: String = "",
    val isLoading: Boolean = false,
    val errors: Map<String, String> = emptyMap()
) {
    val total: Double get() = selectedTicket.price * quantity
}

enum class TicketType(val label: String, val price: Double) {
    Regular("Regular", 1000.0),
    VIP("VIP", 2500.0)
}