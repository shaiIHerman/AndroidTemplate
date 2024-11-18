package com.example.templateapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.screens.DetailsScreen
import com.example.templateapplication.screens.HomeScreen
import com.example.templateapplication.ui.theme.TemplateApplicationTheme
import com.example.templateapplication.ui.theme.TemplatePrimary
import com.example.templateapplication.viewmodels.DetailsScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            TemplateApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationHost(
                        navController = navController, modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    private fun NavigationHost(navController: NavHostController, modifier: Modifier) {
        NavHost(
            navController = navController,
            startDestination = "home_screen",
            modifier = modifier.background(color = TemplatePrimary)
        ) {
            composable(route = "home_screen") {
                HomeScreen { id ->
                    navController.navigate("details_screen/$id")
                }
            }
            composable(route = "details_screen/{id}") { args ->

                // One method is to pass arguments
//                val id = args.arguments?.getString("id") ?: ""
//                DetailsScreen(id = id) details screen would have to hold a parameter of the id

                // The second way is to use SavedStateHandle to pass parameters
                val viewModel = hiltViewModel<DetailsScreenViewModel>()
                DetailsScreen(viewModel = viewModel)
            }
        }
    }
}