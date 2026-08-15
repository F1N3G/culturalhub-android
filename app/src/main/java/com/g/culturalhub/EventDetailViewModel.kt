package com.g.culturalhub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.g.culturalhub.data.EventRepository
import com.g.culturalhub.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface EventDetailUiState {
    data object Loading : EventDetailUiState
    data class Success(val event: Event) : EventDetailUiState
    data class Error(val message: String) : EventDetailUiState
}

class EventDetailViewModel : ViewModel() {

    private val repository = EventRepository()

    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Loading)
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    fun loadEvent(id: Int) {
        viewModelScope.launch {
            _uiState.value = EventDetailUiState.Loading
            try {
                _uiState.value = EventDetailUiState.Success(repository.getEvent(id))
            } catch (e: Exception) {
                _uiState.value = EventDetailUiState.Error(e.message ?: "Ceva n-a mers.")
            }
        }
    }
}