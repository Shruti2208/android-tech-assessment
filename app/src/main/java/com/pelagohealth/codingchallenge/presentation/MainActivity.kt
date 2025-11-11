package com.pelagohealth.codingchallenge

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pelagohealth.codingchallenge.presentation.MainViewModel
import com.pelagohealth.codingchallenge.presentation.SecondViewModel
import com.pelagohealth.codingchallenge.ui.theme.PelagoCodingChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val secondViewModel: SecondViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PelagoCodingChallengeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavHost(secondViewModel = secondViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(secondViewModel: SecondViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            MainScreen(navController = navController, modifier = modifier)
        }
        composable(route = "second") {
            SecondScreen(viewModel = secondViewModel, navController = navController, modifier = modifier)
        }
    }
}

@Composable
fun MainScreen(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val activity = context as? Activity
    LaunchedEffect(activity) {
        activity?.let { viewModel.attach(it) }
    }

    // trap: pass and attach NavController to ViewModel (retains reference)
    LaunchedEffect(navController) {
        viewModel.attachNavController(navController)
    }

    LaunchedEffect(Unit) {
        val observer = LifecycleEventObserver { _, _ -> /* no-op */ }
        lifecycleOwner.lifecycle.addObserver(observer)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = state.current?.text ?: "Hello!",
            modifier = modifier
        )
        Button(onClick = { viewModel.fetchNewFact() }) {
            Text("More facts!")
        }
        Button(onClick = { navController.navigate("second") }) {
            Text("Go to second")
        }
        state.recent.forEach { Text("• ${it.text}") }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PelagoCodingChallengeTheme {
        val navController = rememberNavController()
        MainScreen(navController = navController)
    }
}

@Composable
fun SecondScreen(viewModel: SecondViewModel, navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dummy Second Screen")
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}