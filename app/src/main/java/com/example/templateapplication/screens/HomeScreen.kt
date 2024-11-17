package com.example.templateapplication.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.example.templateapplication.components.ErrorState
import com.example.templateapplication.components.LoadingState
import com.example.templateapplication.network.models.domain.Character
import com.example.templateapplication.ui.theme.TemplateAction
import com.example.templateapplication.viewmodels.HomeScreenViewModel

sealed interface HomeScreenStateView {
    object Loading : HomeScreenStateView
    data class Error(val message: String) : HomeScreenStateView
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
        is HomeScreenStateView.Error -> ErrorState(exception = state.message)
    }
}

@Composable
fun CharactersList(state: HomeScreenStateView.Success) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.clipToBounds()
    ) {
        items(state.characters) { character ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .border(width = 1.dp, shape = RoundedCornerShape(12.dp), color = Color.White)
                    .clip(shape = RoundedCornerShape(12.dp))
            ) {
                SubcomposeAsyncImage(
                    model = character.imageUrl,
                    contentDescription = "character image",
                    loading = { LoadingState() },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .aspectRatio(1f)
                )
                Text(
                    text = character.name,
                    fontSize = 24.sp,
                    color = TemplateAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterVertically)
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                )
            }
        }
    }
}
