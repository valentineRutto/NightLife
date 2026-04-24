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

init {
        fetchEvents()

}
    //private val _uiState = MutableStateFlow(EventUiState())
   // val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()
    private val _allEvents = MutableStateFlow<List<Event>>(emptyList())
    private val _error = MutableStateFlow<String?>(null)

    var genres = emptyList<String>()


    private val _selectedGenre = MutableStateFlow<String?>(null)
    val selectedGenre: StateFlow<String?> = _selectedGenre.asStateFlow()

    private  fun fetchEvents() {
       viewModelScope.launch {
            try {
                eventRepository.fetchEvents()
                eventRepository.getEvents().collect { events ->
                    _allEvents.value = events
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Something went wrong"
            }
        }
}
    val uiState: StateFlow<EventUiState> = combine(
        _allEvents,
        _selectedGenre,
        _error
    ) { events, genre, error ->
        val filtered = if (genre == null) events
        else events.filter { it.category == genre }
        EventUiState(
            events = filtered,
            genres = events.map { it.category }.distinct(),
            isLoading = false,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EventUiState(isLoading = true)
    )


    fun onGenreSelected(genre: String?) {
        _selectedGenre.value = if (genre == "All") null else genre

    }
}

data class EventUiState(
    val events: List<Event> = emptyList(),
    val selectedEvent: Event? = null,
    val genres: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)