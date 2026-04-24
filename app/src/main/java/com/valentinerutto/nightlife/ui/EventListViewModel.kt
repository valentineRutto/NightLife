package com.valentinerutto.nightlife.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.valentinerutto.nightlife.data.Event
import com.valentinerutto.nightlife.data.EventRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EventListViewModel(
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val _selectedGenre = MutableStateFlow<String?>(null)
    val selectedGenre: StateFlow<String?> = _selectedGenre.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
   val events: MutableStateFlow<String?> = _selectedGenre
      //  .flatMapLatest { genre -> eventRepository.getPagedEvents(genre) }
       // .cachedIn(viewModelScope)

    val genres = listOf("All", "Amapiano", "House", "Afrobeats", "Hip Hop", "R&B", "Techno")

    fun onGenreSelected(genre: String?) {
        _selectedGenre.value = if (genre == "All") null else genre
    }

    fun onToggleBookmark(eventId: String) {
        viewModelScope.launch {
        }
    }
}