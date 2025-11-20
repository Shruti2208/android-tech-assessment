package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.pelagohealth.codingchallenge.data.repository.FactRepository
import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.presentation.MainViewModel.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private var repository: FactRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryScreenState())
    val state: StateFlow<HistoryScreenState> = _state

    private lateinit var navController: NavController

    fun attachNavController(controller: NavController) {
        this.navController = controller
    }

    fun navigateHome() {
        navController.navigate("home")
    }

    data class HistoryScreenState(
        val facts: List<Fact>? = null,
        val loading: Boolean = false,
    )
}