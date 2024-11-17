package com.example.templateapplication.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.templateapplication.components.LoadingState
import com.example.templateapplication.network.models.domain.Character
import com.example.templateapplication.ui.theme.Purple40
import com.example.templateapplication.viewmodels.HomeScreenViewModel

sealed interface HomeScreenStateView {
    object Loading : HomeScreenStateView
    data class Success(val characters: List<Character> = emptyList()) : HomeScreenStateView
}

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {

    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewModel, block = {
        viewModel.fetchListData()
    })

    when (val state = viewState) {
        HomeScreenStateView.Loading -> LoadingState()
        is HomeScreenStateView.Success -> CharactersList(state)
    }

}

@Composable
fun CharactersList(state: HomeScreenStateView.Success) {
    LazyColumn {
        items(state.characters) { character ->
            Text(text = character.name, fontSize = 24.sp, color = Purple40)
        }
    }
}
