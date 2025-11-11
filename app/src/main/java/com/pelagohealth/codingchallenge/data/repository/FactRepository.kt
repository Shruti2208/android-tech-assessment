package com.pelagohealth.codingchallenge.data.repository

import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import com.pelagohealth.codingchallenge.domain.model.Fact

/**
 * Repository providing random facts.
 */
class FactRepository(
    private val api: FactsRestApi
) {
    
    suspend fun get(): Fact {
        val dto = api.getFact()
        return Fact(text = dto.text, url = dto.sourceUrl)
    }

}