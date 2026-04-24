package com.valentinerutto.nightlife.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valentinerutto.nightlife.data.BookingStep
import com.valentinerutto.nightlife.data.Event
import com.valentinerutto.nightlife.ui.EventDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventDetailsScreen(eventId: String, onBack: () -> Unit) {
    val viewModel: EventDetailsViewModel=koinViewModel()
    val bookingState by viewModel.bookingState.collectAsStateWithLifecycle()

val event = viewModel.eventByID(eventId)

    AnimatedContent(
        targetState = bookingState.step,
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
        }
    ) { step ->
        when (step) {
            BookingStep.Details -> TicketSelectionContent(
                event = event,
                state = bookingState,
                onBack = onBack,
                onSelectTicket = viewModel::selectTicket,
                onChangeQty = viewModel::setQuantity,
                onBookNow = { viewModel.goToStep(BookingStep.Form) }
            )
            BookingStep.Form -> BookingFormContent(
                state = bookingState,
                onBack = { viewModel.goToStep(BookingStep.Details) },
                onFieldChange = viewModel::updateField,
                onContinue = viewModel::validateAndProceed
            )
            BookingStep.Confirm -> ConfirmContent(
                event = event,
                state = bookingState,
                onBack = { viewModel.goToStep(BookingStep.Form) },
                onConfirm = viewModel::confirmBooking
            )
            BookingStep.Success -> SuccessContent(
                state = bookingState,
                onDone = onBack
            )

            else -> {}
        }

    }
}