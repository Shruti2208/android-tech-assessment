package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.pelagohealth.codingchallenge.data.repository.FactRepository
import com.pelagohealth.codingchallenge.domain.model.Fact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: FactRepository
) : ViewModel() {

    val history: StateFlow<List<Fact>> = repository.history

    private lateinit var navController: NavController

    fun attachNavController(controller: NavController) {
        this.navController = controller
    }

    fun navigateHome() {
        navController.popBackStack()
    }
}