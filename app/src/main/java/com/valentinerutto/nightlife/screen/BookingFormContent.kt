package com.valentinerutto.nightlife.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.valentinerutto.nightlife.data.BookingUiState
import com.valentinerutto.nightlife.data.Event

@Composable
fun BookingFormContent(
    state: BookingUiState,
    event: Event,
    onBack: () -> Unit,
    onFieldChange: (String, String) -> Unit,
    onContinue: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar
        BookingTopBar(title = "Your details", onBack = onBack)
        BookingStepIndicator(currentStep = 1)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BookingTextField(
                    modifier = Modifier.weight(1f),
                    label = "First name",
                    value = state.firstName,
                    error = state.errors["firstName"],
                    onValueChange = { onFieldChange("firstName", it) }
                )
                BookingTextField(
                    modifier = Modifier.weight(1f),
                    label = "Last name",
                    value = state.lastName,
                    error = state.errors["lastName"],
                    onValueChange = { onFieldChange("lastName", it) }
                )
            }
            Spacer(Modifier.height(4.dp))
            BookingTextField(
                label = "Email address",
                value = state.email,
                error = state.errors["email"],
                keyboardType = KeyboardType.Email,
                onValueChange = { onFieldChange("email", it) }
            )
            BookingTextField(
                label = "Phone number",
                value = state.phone,
                error = state.errors["phone"],
                keyboardType = KeyboardType.Phone,
                placeholder = "+254 7XX XXX XXX",
                onValueChange = { onFieldChange("phone", it) }
            )
            Spacer(Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))
            // Mini order summary
            OrderSummaryCard(state = state,event)
        }

        // Continue button
        Surface(shadowElevation = 4.dp, color = MaterialTheme.colorScheme.surface) {
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}