package com.noam.repos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.noam.repos.model.TimeFrame
import com.noam.repos.ui.screens.RepositoriesScreen
import com.noam.repos.ui.screens.Screens
import com.noam.repos.ui.screens.WelcomeScreen
import com.noam.repos.ui.theme.ReposTheme
import com.noam.repos.viewmodel.RepositoriesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReposTheme {
                val repositoriesViewModel = koinViewModel<RepositoriesViewModel>()
                LaunchedEffect(baseContext) {
                    withContext(Dispatchers.IO) {
                        repositoriesViewModel.fetchRepositories(timeframe = TimeFrame.LastWeek)
                    }
                }
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screens.WelcomeScreen.route
                ) {
                    composable(route = Screens.WelcomeScreen.route) { WelcomeScreen(navController) }
                    composable(route = Screens.ReposMainScreen.route + "/{timeFrame}") { backStackEntry ->
                        val timeFrame = backStackEntry.arguments?.getString("timeFrame")?.let{ TimeFrame.valueOf(it) } ?: TimeFrame.LastWeek
                        RepositoriesScreen(navController, timeFrame)
                    }
                }
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    RepositoriesScreen(innerPadding)
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReposTheme {
        Greeting("Android")
    }
}