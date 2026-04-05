package com.example.project.feature.decision.data

import com.example.project.feature.decision.data.local.DecidrDao
import com.example.project.feature.decision.data.local.DecisionEntity
import com.example.project.feature.decision.data.local.DecisionWithDetails
import com.example.project.feature.decision.data.local.OptionEntity
import com.example.project.feature.decision.data.local.FactorEntity
import com.example.project.feature.decision.domain.Decision
import com.example.project.feature.decision.domain.Option
import com.example.project.feature.decision.domain.Factor
import com.example.project.feature.decision.domain.DecisionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstDecisionRepository(
    private val decidrDao: DecidrDao
) : DecisionRepository {

    override suspend fun saveDecision(decision: Decision) {
        val decisionEntity = DecisionEntity(
            id = decision.id,
            query = decision.query,
            timestamp = System.currentTimeMillis()
        )
        decidrDao.insertDecision(decisionEntity)

        val optionEntities = decision.options.map { option ->
            OptionEntity(
                id = option.id,
                decisionId = decision.id,
                title = option.title,
                description = option.description
            )
        }
        decidrDao.insertOptions(optionEntities)

        val factorEntities = decision.factors.map { factor ->
            FactorEntity(
                id = factor.id,
                decisionId = decision.id,
                name = factor.name,
                weight = factor.weight
            )
        }
        decidrDao.insertFactors(factorEntities)
    }

    override suspend fun deleteDecision(decisionId: String) {
        decidrDao.deleteDecisionById(decisionId)
    }

    override fun getDecision(decisionId: String): Flow<Decision?> {
        return decidrDao.getDecisionWithDetails(decisionId).map { entity ->
            entity?.toDomainModel()
        }
    }

    override fun getAllDecisions(): Flow<List<Decision>> {
        return decidrDao.getAllDecisionsWithDetails().map { list ->
            list.map { it.toDomainModel() }
        }
    }

    private fun DecisionWithDetails.toDomainModel(): Decision {
        return Decision(
            id = this.decision.id,
            query = this.decision.query,
            options = this.options.map { Option(it.id, it.title, it.description) },
            factors = this.factors.map { Factor(it.id, it.name, it.weight) }
        )
    }
}
