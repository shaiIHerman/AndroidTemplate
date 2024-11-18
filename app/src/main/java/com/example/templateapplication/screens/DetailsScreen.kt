package com.example.templateapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.templateapplication.components.ErrorState
import com.example.templateapplication.components.LoadingState
import com.example.templateapplication.network.models.domain.Character
import com.example.templateapplication.ui.theme.TemplateAction
import com.example.templateapplication.ui.theme.TemplateTextPrimary
import com.example.templateapplication.viewmodels.DetailsScreenViewModel


sealed interface DetailsScreenStateView {
    object Loading : DetailsScreenStateView
    data class Error(val message: String) : DetailsScreenStateView
    data class Success(val item: Character) : DetailsScreenStateView
}

@Composable
fun DetailsScreen(viewModel: DetailsScreenViewModel) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.fetchObjectById()
    }

    when (val state = viewState) {
        DetailsScreenStateView.Loading -> LoadingState()
        is DetailsScreenStateView.Error -> ErrorState(exception = state.message)
        is DetailsScreenStateView.Success -> ItemDetails(item = state.item)
    }
}

@Composable
fun ItemDetails(item: Character) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
        item {
            SubcomposeAsyncImage(
                model = item.imageUrl,
                contentDescription = "item image",
                loading = { LoadingState() },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .aspectRatio(1f)
            )
        }

        item {
            Spacer(modifier = Modifier.height(15.dp))
            DetailsItem("Name", item.name)
        }
        item {
            DetailsItem("Status", item.status.displayName)
        }
    }
}

@Composable
fun DetailsItem(key: String, value: String) {
    Column {
        Text(text = key, color = TemplateAction, fontSize = 24.sp)
        Text(text = value, color = TemplateTextPrimary, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(15.dp))
    }
}
