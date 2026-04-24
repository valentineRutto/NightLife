package com.valentinerutto.nightlife.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.valentinerutto.nightlife.data.BookingUiState
import com.valentinerutto.nightlife.data.Event

@Composable
fun ConfirmContent(
    event: Event,
    state: BookingUiState,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BookingTopBar(title = "Confirm booking", onBack = onBack)
        BookingStepIndicator(currentStep = 2)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Full order summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    listOf(
                        "Event"    to event.title,
                        "Date"     to event.dateTime.toFormattedDate(),
                        "Location" to event.location,
                        "Ticket"   to "${state.selectedTicket.label} × ${state.quantity}",
                        "Name"     to "${state.firstName} ${state.lastName}",
                        "Email"    to state.email,
                        "Phone"    to state.phone,
                    ).forEach { (label, value) ->
                        SummaryRow(label = label, value = value)
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    SummaryRow(
                        label = "Total",
                        value = "KSh ${state.total.toFormattedPrice()}",
                        isTotal = true
                    )
                }
            }
        }

        Surface(shadowElevation = 4.dp, color = MaterialTheme.colorScheme.surface) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Button(
                    onClick = onConfirm,
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Confirm & Pay KSh ${state.total.toFormattedPrice()}",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}