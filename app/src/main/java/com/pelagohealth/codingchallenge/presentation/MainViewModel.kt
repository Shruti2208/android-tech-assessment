package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelagohealth.codingchallenge.data.repository.FactRepository
import com.pelagohealth.codingchallenge.domain.model.Fact
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.navigation.NavController

@HiltViewModel
class MainViewModel @Inject constructor(
    private var repository: FactRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state
    private lateinit var navController: NavController

    fun attachNavController(controller: NavController) {
        this.navController = controller
    }

    init {
        GlobalScope.launch {
            fetchNewFact()
        }
    }

    fun navigateToSecondScreen() {
        navController.navigate("history")
    }

    fun fetchNewFact() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching { repository.get() }
                .onSuccess { fact ->
                    val prev = _state.value.current
                    val recent = (listOfNotNull(prev) + _state.value.recent).take(3)
                    _state.value = MainScreenState(current = fact, recent = recent)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(loading = false, error = e.message ?: "Error")
                }
        }
    }

    data class MainScreenState(
        val current: Fact? = null,
        val recent: List<Fact> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null
    )
}