package com.example.templateapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapplication.repository.DataRepository
import com.example.templateapplication.screens.HomeScreenStateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private val _viewState = MutableStateFlow<HomeScreenStateView>(HomeScreenStateView.Loading)
    val viewState: StateFlow<HomeScreenStateView> = _viewState.asStateFlow()

    fun fetchListData() = viewModelScope.launch {
        val data = dataRepository.getDataList()
        data.onSuccess { characters ->
            _viewState.update { return@update HomeScreenStateView.Success(itemList = characters) }
        }.onFailure { e ->
            _viewState.update { return@update HomeScreenStateView.Error(e.message ?: "") }
        }
    }
}