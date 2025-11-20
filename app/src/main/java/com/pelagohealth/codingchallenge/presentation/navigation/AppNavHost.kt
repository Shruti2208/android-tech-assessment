package com.pelagohealth.codingchallenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pelagohealth.codingchallenge.presentation.HistoryScreen
import com.pelagohealth.codingchallenge.presentation.HistoryViewModel
import com.pelagohealth.codingchallenge.presentation.MainScreen
import com.pelagohealth.codingchallenge.presentation.MainViewModel

@Composable
fun AppNavHost(
    mainViewModel: MainViewModel,
    historyScreenViewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            MainScreen(
                viewModel = mainViewModel,
                navController = navController,
                modifier = modifier,
            )
        }
        composable(route = "history") {
            HistoryScreen(
                viewModel = historyScreenViewModel,
                navController = navController,
                modifier = modifier
            )
        }
    }
}

