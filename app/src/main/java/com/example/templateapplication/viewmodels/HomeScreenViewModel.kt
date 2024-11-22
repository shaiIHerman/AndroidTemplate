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
    private var nextPage = 0

    fun fetchListData() = viewModelScope.launch {
        val data = dataRepository.getDataList()
        data.onSuccess { items ->
            nextPage++
            _viewState.update { return@update HomeScreenStateView.Success(itemList = items) }
        }.onFailure { e ->
            _viewState.update { return@update HomeScreenStateView.Error(e.message ?: "") }
        }
    }

    fun onLoadMore() = viewModelScope.launch {
        val data = dataRepository.getNextPage(nextPage)
        data.onSuccess { items ->
            nextPage++
            _viewState.update { currentState ->
                val currentItems =
                    (currentState as? HomeScreenStateView.Success)?.itemList ?: emptyList()
                return@update HomeScreenStateView.Success(itemList = currentItems + items)
            }
        }.onFailure { e ->
            _viewState.update { return@update HomeScreenStateView.Error(e.message ?: "") }
        }
    }
}