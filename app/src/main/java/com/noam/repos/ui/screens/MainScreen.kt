package com.noam.repos.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.noam.repos.model.TimeFrame
import com.noam.repos.ui.model.BottomNavigationItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(timeFrame: TimeFrame) {
    val innerNavController = rememberNavController()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomNavigation = when {
        currentRoute?.equals(Screens.RepoDetailsScreen.route) == true -> false
        else -> true
    }
    val showBackButton = when {
        currentRoute?.equals(Screens.RepoDetailsScreen.route) == true -> true
        else -> false
    }

    val topBarTitle = when {
        currentRoute?.equals(Screens.RepoDetailsScreen.route) == true -> "Repo Details"
        currentRoute?.equals(Screens.ReposMainScreen.route) == true -> "All Repositories"
        currentRoute?.equals(Screens.ReposFavoritesScreen.route) == true -> "Favorites"
        else -> "Repositories"
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = topBarTitle)
                },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = {
                            // Handle back navigation
                            innerNavController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    }
                },
            )
        },
        bottomBar = {
            if (showBottomNavigation) {
                NavigationBar {
                    bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
//                   val isSelected = bottomNavigationItem.bottomNavigationTitle.route == rootNavBackStackEntry?.destination?.route
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                innerNavController.navigate(bottomNavigationItem.bottomNavigationTitle.host) {
                                    popUpTo(innerNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = {
                                Text(
                                    text = bottomNavigationItem.bottomNavigationTitle.title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                ) },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (bottomNavigationItem.badgeCount != null) {
                                            Badge { Text(text = bottomNavigationItem.badgeCount.toString())
                                            }
                                        } else if (bottomNavigationItem.hasNews) {
                                            Badge()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (selectedItemIndex == index) {
                                            bottomNavigationItem.selectedIcon
                                        } else {
                                            bottomNavigationItem.unselectedIcon
                                        },
                                        contentDescription = bottomNavigationItem.bottomNavigationTitle.title,)
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(navController = innerNavController, startDestination = Screens.ReposMainScreen.route) {
            composable(route = Screens.ReposMainScreen.route) {
//                val argTimeFrame = backStackEntry.arguments?.getString("timeFrame")?.let{ TimeFrame.valueOf(it) } ?: TimeFrame.LastDay
                RepositoriesScreen(innerNavController, timeFrame, scaffoldPadding = padding)
            }
            composable(route = Screens.RepoDetailsScreen.route) {
                RepoDetailsScreen(
                    scaffoldPadding = padding,
                    onBackClicked = {
                        innerNavController.popBackStack()
                    }
                )
            }
            composable(route = Screens.ReposFavoritesScreen.route) {
                FavoriteReposScreen(
                    scaffoldPadding = padding
                )
            }
        }
    }
}

private val bottomNavigationItems = listOf(
    BottomNavigationItem(
        bottomNavigationTitle = BottomNavigationTitle.AllRepos,
        selectedIcon = Icons.Filled.AccountBox,
        unselectedIcon = Icons.Outlined.AccountBox,
        hasNews = false,
        badgeCount = null
    ),
    BottomNavigationItem(
        bottomNavigationTitle = BottomNavigationTitle.Favorites,
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Filled.FavoriteBorder,
        hasNews = false,
        badgeCount = null
    ),
)

sealed class BottomNavigationTitle(val title: String, val route: String, val host: String) {
    object AllRepos : BottomNavigationTitle("Dream", "AllRepositoriesScreen", Screens.ReposMainScreen.route)
    object Favorites : BottomNavigationTitle("Favorites", "FavoriteReposScreen", Screens.ReposFavoritesScreen.route)
}