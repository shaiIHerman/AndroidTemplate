package com.example.templateapplication.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapplication.repository.DataRepository
import com.example.templateapplication.screens.DetailsScreenStateView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val id: String? = savedStateHandle["id"]
    private val _viewState =
        MutableStateFlow<DetailsScreenStateView>(DetailsScreenStateView.Loading)
    val viewState: StateFlow<DetailsScreenStateView> = _viewState.asStateFlow()

    fun fetchObjectById() = viewModelScope.launch {
        val data = dataRepository.getDataObject(id?.toInt() ?: -1)
        data.onSuccess { item ->
            _viewState.update { return@update DetailsScreenStateView.Success(item = item) }
        }.onFailure { e ->
            _viewState.update { return@update DetailsScreenStateView.Error(e.message ?: "") }
        }
    }
}