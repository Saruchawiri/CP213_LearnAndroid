package com.example.project.feature.decision.data

import com.example.project.feature.decision.data.local.DecisionDao
import com.example.project.feature.decision.data.local.DecisionEntity
import com.example.project.feature.decision.domain.Decision
import com.example.project.feature.decision.domain.DecisionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.project.feature.decision.domain.Option
import com.example.project.feature.decision.domain.Factor

class OfflineFirstDecisionRepository(
    private val decisionDao: DecisionDao,
    private val gson: Gson
) : DecisionRepository {

    override suspend fun saveDecision(decision: Decision) {
        val entity = DecisionEntity(
            id = decision.id,
            query = decision.query,
            optionsJson = gson.toJson(decision.options),
            factorsJson = gson.toJson(decision.factors)
        )
        decisionDao.insertDecision(entity)
    }

    override suspend fun deleteDecision(decisionId: String) {
        decisionDao.deleteDecisionById(decisionId)
    }

    override fun getDecision(decisionId: String): Flow<Decision?> {
        return decisionDao.getDecisionById(decisionId).map { entity ->
            entity?.toDomainModel()
        }
    }

    override fun getAllDecisions(): Flow<List<Decision>> {
        return decisionDao.getAllDecisions().map { list ->
            list.map { it.toDomainModel() }
        }
    }

    private fun DecisionEntity.toDomainModel(): Decision {
        val optionsType = object : TypeToken<List<Option>>() {}.type
        val factorsType = object : TypeToken<List<Factor>>() {}.type

        val optionsList: List<Option> = gson.fromJson(this.optionsJson, optionsType) ?: emptyList()
        val factorsList: List<Factor> = gson.fromJson(this.factorsJson, factorsType) ?: emptyList()

        return Decision(
            id = this.id,
            query = this.query,
            options = optionsList,
            factors = factorsList
        )
    }
}
