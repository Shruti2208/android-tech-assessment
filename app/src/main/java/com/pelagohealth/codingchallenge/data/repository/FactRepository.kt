package com.pelagohealth.codingchallenge.data.repository

import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import com.pelagohealth.codingchallenge.domain.model.Fact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository providing random facts.
 */
class FactRepository @Inject constructor(
    private val api: FactsRestApi
) {
    
    // FIX: Changed from Dispatchers.Default to Dispatchers.IO for network operations
    suspend fun get(): Fact = withContext(Dispatchers.IO) {
        val dto = api.getFact()
        Fact(text = dto.text, url = dto.sourceUrl)
    }

}