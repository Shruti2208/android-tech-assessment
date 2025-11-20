package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelagohealth.codingchallenge.data.repository.FactRepository
import com.pelagohealth.codingchallenge.domain.model.Fact
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        CoroutineScope(Dispatchers.Main).launch {
            _state.value = MainScreenState(loading = true)
            runCatching { repository.get() }
                .onSuccess { fact ->
                    _state.value = MainScreenState(current = fact)
                }
                .onFailure { e ->
                    println(e)
                }
        }
    }

    data class MainScreenState(
        val current: Fact? = null,
        val loading: Boolean = false,
    )
}