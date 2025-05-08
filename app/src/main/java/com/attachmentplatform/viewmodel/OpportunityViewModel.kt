package com.attachmentplatform.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attachmentplatform.student.data.Opportunity
import com.attachmentplatform.student.data.OpportunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}

@HiltViewModel
class OpportunityViewModel @Inject constructor(
    private val repository: OpportunityRepository
) : ViewModel() {

    private val _creationState = MutableStateFlow<UiState>(UiState.Idle)
    val creationState: StateFlow<UiState> = _creationState

    private val _opportunitiesList = MutableStateFlow<List<Opportunity>>(emptyList())
    val opportunitiesList: StateFlow<List<Opportunity>> = _opportunitiesList

    fun createOpportunity(
        companyName: String,
        title: String,
        description: String,
        requirements: String,
        location: String,
        duration: String,
        slots: Int,
        deadline: String
    ) {
        viewModelScope.launch {
            _creationState.value = UiState.Loading
            try {
                val opportunity = Opportunity(
                    companyName = companyName,
                    title = title,
                    description = description,
                    requirements = requirements,
                    location = location,
                    duration = duration,
                    availableSlots = slots,
                    applicationDeadline = deadline
                )
                repository.createOpportunity(
                    opportunity,
                    onSuccess = {
                        _creationState.value = UiState.Success
                    },
                    onFailure = {
                        _creationState.value = UiState.Error(it.message ?: "Unknown error")
                    }
                )
            } catch (e: Exception) {
                _creationState.value = UiState.Error(e.message ?: "Unexpected error")
            }
        }
    }

    fun loadOpportunities() {
        viewModelScope.launch {
            repository.getOpportunitiesStream()
                .catch { e ->
                    _creationState.value = UiState.Error(e.message ?: "Failed to load")
                }
                .collect { list ->
                    _opportunitiesList.value = list
                }
        }
    }
}
