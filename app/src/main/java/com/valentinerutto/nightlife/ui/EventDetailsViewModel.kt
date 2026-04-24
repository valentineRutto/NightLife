package com.valentinerutto.nightlife.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentinerutto.nightlife.data.BookingStep
import com.valentinerutto.nightlife.data.BookingUiState
import com.valentinerutto.nightlife.data.EventRepository
import com.valentinerutto.nightlife.data.TicketType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventDetailsViewModel(private val repository: EventRepository)
 : ViewModel() {

    private val _bookingState = MutableStateFlow(BookingUiState())
    val bookingState: StateFlow<BookingUiState> = _bookingState.asStateFlow()

    fun selectTicket(type: TicketType) = _bookingState.update { it.copy(selectedTicket = type) }
    fun setQuantity(qty: Int) = _bookingState.update { it.copy(quantity = qty.coerceIn(1, 10)) }
    fun updateField(field: String, value: String) = _bookingState.update {
        it.copy(
            firstName = if (field == "firstName") value else it.firstName,
            lastName  = if (field == "lastName")  value else it.lastName,
            email     = if (field == "email")      value else it.email,
            phone     = if (field == "phone")      value else it.phone,
            errors    = it.errors - field
        )
    }

    fun goToStep(step: BookingStep) = _bookingState.update { it.copy(step = step) }

    fun validateAndProceed() {
        val s = _bookingState.value
        val errors = buildMap {
            if (s.firstName.isBlank()) put("firstName", "Required")
            if (s.lastName.isBlank())  put("lastName", "Required")
            if (!s.email.contains("@")) put("email", "Enter a valid email")
            if (s.phone.isBlank())     put("phone", "Required")
        }
        if (errors.isEmpty()) goToStep(BookingStep.Confirm)
        else _bookingState.update { it.copy(errors = errors) }
    }

    fun confirmBooking() {
        viewModelScope.launch {
            _bookingState.update { it.copy(isLoading = true) }
            try {
                val ref = repository.bookEvent(_bookingState.value)
                _bookingState.update { it.copy(step = BookingStep.Success, bookingRef = ref, isLoading = false) }
            } catch (e: Exception) {
                _bookingState.update { it.copy(isLoading = false) }
            }
        }
    }
}