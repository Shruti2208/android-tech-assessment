package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelagohealth.codingchallenge.data.repository.FactRepository
import com.pelagohealth.codingchallenge.domain.model.Fact
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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

    // FIX: Replaced GlobalScope with viewModelScope to properly tie coroutine lifecycle to ViewModel
    init {
        viewModelScope.launch {
            fetchNewFact()
        }
    }

    fun navigateToSecondScreen() {
        navController.navigate("history")
    }

    // FIX: Replaced CoroutineScope(Dispatchers.Main) with viewModelScope
    // FIX: Added proper error handling with error state instead of just println
    fun fetchNewFact() {
        viewModelScope.launch {
            val previousFact = _state.value.current
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching { repository.get() }
                .onSuccess { fact ->
                    // Add previous fact to history only after new fact is successfully fetched
                    previousFact?.let { repository.addToHistory(it) }
                    _state.value = MainScreenState(current = fact)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(loading = false, error = e.message ?: "Unknown error")
                }
        }
    }

    data class MainScreenState(
        val current: Fact? = null,
        val loading: Boolean = false,
        val error: String? = null,
    )
}