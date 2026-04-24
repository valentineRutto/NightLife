package com.valentinerutto.nightlife.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
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
    val events = listOf("","")
    val selectedGenre by viewModel.selectedGenre.collectAsState()

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
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(viewModel.genres) { genre ->
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
//            if (events.loadState.refresh is LoadState.Loading) {
//                items(5) { EventCardSkeleton() }
//            }
//
//            // Error state
//            if (events.loadState.refresh is LoadState.Error) {
//                item {
//                    ErrorBanner(
//                        message = "Failed to load events. Showing cached data.",
//                        onRetry = { events.retry() },
//                    )
//                }
//            }
//
//            // Event cards
//            items(count = events.itemCount) { index ->
//                val event = events[index]
//                if (event != null) {
//                    EventCard(
//                        event = event,
//                        onClick = { onEventClick(event.id) },
//                        onBookmark = { viewModel.onToggleBookmark(event.id) },
//                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
//                    )
//                } else {
//                    EventCardSkeleton()
//                }
//            }

            // Append loading
        //    if (events.loadState.append is LoadState.Loading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
             //   }
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onBookmark: () -> Unit,
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
            }

            // Bookmark button
            IconButton(
                onClick = onBookmark,
                modifier = Modifier.align(Alignment.TopEnd).padding(4.dp),
            ) {
//                Icon(
//                    imageVector = if (event.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
//                    contentDescription = "Bookmark",
//                    tint = Color.White,
//                )
            }

            // Event info at bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
            ) {
                // Genre chip
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(bottom = 6.dp),
                ) {
                    Text(
                        event.genre,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
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
                        "${event.venue}  ·  ${dateFormat.format(Date(event.dateTimeEpoch))}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                    )
                    Text(
                        "From KES ${priceFormat.format(event.minPrice)}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
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