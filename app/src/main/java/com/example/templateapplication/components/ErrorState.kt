package com.example.templateapplication.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.templateapplication.ui.theme.TemplateAction

private val defaultModifier = Modifier
    .fillMaxSize()
    .padding(all = 128.dp)

@Composable
fun ErrorState(modifier: Modifier = defaultModifier, exception: String?) {
    Text(
        text = exception ?: " An error has occurred",
        modifier = modifier,
        color = TemplateAction
    )
}