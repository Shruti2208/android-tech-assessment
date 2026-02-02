package com.pelagohealth.codingchallenge.data.repository

import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import com.pelagohealth.codingchallenge.domain.model.Fact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository providing random facts and managing history.
 *
 * TODO: Add unit tests for FactRepository:
 * - Test get() maps API response to domain model correctly
 * - Test addToHistory() adds fact to front of list
 * - Test history limited to 10 items (oldest removed)
 * - Use MockWebServer or mock FactsRestApi
 */
@Singleton
class FactRepository @Inject constructor(
    private val api: FactsRestApi
) {

    private val _history = MutableStateFlow<List<Fact>>(emptyList())
    val history: StateFlow<List<Fact>> = _history.asStateFlow()

    // FIX: Changed from Dispatchers.Default to Dispatchers.IO for network operations
    suspend fun get(): Fact = withContext(Dispatchers.IO) {
        val dto = api.getFact()
        Fact(text = dto.text, url = dto.sourceUrl)
    }

    /**
     * Adds a fact to history. Keeps last 10 items with newest first.
     */
    fun addToHistory(fact: Fact) {
        val current = _history.value.toMutableList()
        current.add(0, fact)
        if (current.size > MAX_HISTORY_SIZE) {
            current.removeAt(current.lastIndex)
        }
        _history.value = current
    }

    companion object {
        private const val MAX_HISTORY_SIZE = 10
    }
}