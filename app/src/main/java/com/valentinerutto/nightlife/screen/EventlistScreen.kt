package com.valentinerutto.nightlife.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.valentinerutto.nightlife.data.Event
import com.valentinerutto.nightlife.ui.EventListViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventlistScreen(
    onEventClick: (String) -> Unit,
    viewModel: EventListViewModel = koinViewModel(),
) {
    val events = viewModel.uiState.collectAsStateWithLifecycle()
    val selectedGenre by viewModel.selectedGenre.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tonight", style = MaterialTheme.typography.headlineMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding(),
                bottom = 16.dp,
            ),
        ) {
            val allGenres = listOf("All") + events.value.genres

            item {

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(allGenres  ) { genre ->
                        val isSelected = if (genre == "All") selectedGenre == null
                        else genre == selectedGenre
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.onGenreSelected(genre) },
                            label = { Text(genre) },
                        )
                    }
                }
            }

            // Loading shimmer on first load
            if (events.value.isLoading) {
                items(5) { EventCardSkeleton() }
            }

            // Error state
            if (events.value.error != null) {
                item {
                    ErrorBanner(
                        message = "Failed to load events. Showing cached data.",
                        onRetry = { },
                    )
                }
            }
            if (events.value.events.isNotEmpty() ) {

                // Event cards
                items(count = events.value.events.size) { index ->
                    val event = events.value.events.getOrNull(index)
                    if (event != null) {
                        EventCard(
                            event = event,
                            onClick = { onEventClick(event.id) },
                            onBookNow = { },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        )
                    } else {
                        EventCardSkeleton()
                    }
                }
            }

           if (events.value.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onBookNow: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dateFormat = remember { SimpleDateFormat("EEE dd MMM · HH:mm", Locale.getDefault()) }
    val priceFormat = remember { NumberFormat.getNumberInstance(Locale("sw", "KE")) }

    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box(modifier = Modifier.height(220.dp)) {
            // Hero image
            AsyncImage(
                model = event.imageUrl,
                contentDescription = event.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            // Gradient scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f)),
                            startY = 80f,
                        )
                    )
            )

            // Sold out badge
            if (event.isSoldOut) {
                Surface(
                    modifier = Modifier.align(Alignment.TopStart).padding(12.dp),
                    color = MaterialTheme.colorScheme.error,
                    shape = RoundedCornerShape(6.dp),
                ) {
                    Text(
                        "SOLD OUT",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onError,
                    )
                }
            }else{
                Surface(
                    modifier = Modifier.align(Alignment.TopStart).padding(12.dp),
                    color = MaterialTheme.colorScheme.onSecondary,
                    shape = RoundedCornerShape(6.dp),onClick = onBookNow
                ) {
                        Text(
                        "BOOK NOW",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                        )


                }

            }


            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(bottom = 6.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                    Text(
                     event.category,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "From KES ${priceFormat.format(event.price)}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                        )
                    }

                }
                Text(
                    event.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    maxLines = 1,
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        "${event.description}  ·  ${dateFormat.format(Date(event.dateTime))}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.weight(1f),
                        maxLines = 5,
                    )

                }
            }
        }
    }
}

@Composable
fun EventCardSkeleton() {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
    )
}

@Composable
fun ErrorBanner(message: String, onRetry: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.weight(1f),
        )
        TextButton(onClick = onRetry) { Text("Retry") }
    }
}