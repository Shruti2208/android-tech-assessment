package com.pelagohealth.codingchallenge.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pelagohealth.codingchallenge.R

// TODO: Add UI tests using Compose Testing:
// - Test loading state shows CircularProgressIndicator
// - Test error state displays error message
// - Test fact displayed when loaded
// - Test "More facts!" button triggers fetchNewFact()
// - Test buttons disabled during loading
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
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.random_fact),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    state.loading -> {
                        CircularProgressIndicator()
                    }
                    state.error != null -> {
                        state.error?.let { error ->
                            Text(
                                text = error,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    state.current != null -> {
                        state.current?.let { fact ->
                            Text(
                                text = fact.text,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { viewModel.fetchNewFact() },
            enabled = !state.loading
        ) {
            Text(stringResource(R.string.more_facts))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.navigateToSecondScreen() },
            enabled = !state.loading
        ) {
            Text(stringResource(R.string.show_history))
        }
    }
}

