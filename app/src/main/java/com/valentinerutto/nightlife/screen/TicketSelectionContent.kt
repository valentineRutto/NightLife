package com.valentinerutto.nightlife.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.valentinerutto.nightlife.data.BookingUiState
import com.valentinerutto.nightlife.data.Event
import com.valentinerutto.nightlife.data.TicketType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketSelectionContent(
    event: Event,
    state: BookingUiState,
    onBack: () -> Unit,
    onSelectTicket: (TicketType) -> Unit,
    onChangeQty: (Int) -> Unit,
    onBookNow: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item {
                Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                    AsyncImage(
                        model = event.imageUrl,
                        contentDescription = event.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Gradient scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.65f)),
                                    startY = 100f
                                )
                            )
                    )
                    // Back button
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(12.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    // Category badge
                    Surface(
                        modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f)
                    ) {
                        Text(
                            text = event.category,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    // Sold out ribbon
                    if (event.isSoldOut) {
                        Surface(
                            modifier = Modifier.align(Alignment.BottomStart).padding(12.dp),
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.error
                        ) {
                            Text(
                                "SOLD OUT",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onError
                            )
                        }
                    }
                }
            }

            // ── Event info ──────────────────────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        MetaItem(icon = "📍", text = event.location)
                        MetaItem(icon = "📅", text = event.dateTime.toFormattedDate())
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    )
                }
            }

            item { HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp)) }

            // ── Ticket selection ────────────────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    Text(
                        "Select ticket",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(12.dp))
                    TicketType.entries.forEach { ticketType ->
                        TicketCard(
                            ticketType = ticketType,
                            isSelected = state.selectedTicket == ticketType,
                            onClick = { onSelectTicket(ticketType) }
                        )
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }

            item { HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp)) }

            // ── Quantity + total ────────────────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Quantity", style = MaterialTheme.typography.bodyLarge)
                        QuantitySelector(
                            value = state.quantity,
                            onDecrement = { onChangeQty(state.quantity - 1) },
                            onIncrement = { onChangeQty(state.quantity + 1) }
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total", style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            "KSh ${state.total.toFormattedPrice()}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Bottom padding for FAB
            item { Spacer(Modifier.height(90.dp)) }
        }

        // ── Book Now FAB ─────────────────────────────────────────────
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Button(
                onClick = onBookNow,
                enabled = !event.isSoldOut,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (event.isSoldOut) "Sold Out" else "Book Now",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}