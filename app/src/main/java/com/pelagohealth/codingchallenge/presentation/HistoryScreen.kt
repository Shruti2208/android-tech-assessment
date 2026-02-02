package com.pelagohealth.codingchallenge.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val history by viewModel.history.collectAsState()

    BackHandler {
        viewModel.navigateHome()
    }

    LaunchedEffect(navController) {
        viewModel.attachNavController(navController)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (history.isEmpty()) {
            Text(text = "No history yet")
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(history) { fact ->
                    Text(
                        text = fact.text,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    HorizontalDivider()
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.navigateHome() }) {
            Text("Back")
        }
    }
}

