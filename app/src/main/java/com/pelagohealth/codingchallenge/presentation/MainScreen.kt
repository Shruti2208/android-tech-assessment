package com.pelagohealth.codingchallenge.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(navController) {
        viewModel.attachNavController(navController)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.loading -> {
                CircularProgressIndicator()
            }
            state.error != null -> {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
            state.current != null -> {
                Text(
                    text = state.current!!.text,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.fetchNewFact() },
            enabled = !state.loading
        ) {
            Text("More facts!")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.navigateToSecondScreen() },
            enabled = !state.loading
        ) {
            Text("Show history")
        }
    }
}

