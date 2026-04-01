package com.example.project.feature.decision.domain

import kotlinx.coroutines.flow.Flow

interface DecisionRepository {
    suspend fun saveDecision(decision: Decision)
    suspend fun deleteDecision(decisionId: String)
    fun getDecision(decisionId: String): Flow<Decision?>
    fun getAllDecisions(): Flow<List<Decision>>
}
