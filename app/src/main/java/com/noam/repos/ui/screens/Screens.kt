package com.noam.repos.ui.screens

sealed class Screens(val route: String) {
    object WelcomeScreen : Screens("WelcomeScreen")
    object MainScreen : Screens("MainScreen")

    object ReposMainScreen : Screens("ReposMainScreen")
    object ReposFavoritesScreen : Screens("ReposFavoritesMainScreen")
    object RepoDetailsScreen : Screens("RepoDetailsScreen")
}