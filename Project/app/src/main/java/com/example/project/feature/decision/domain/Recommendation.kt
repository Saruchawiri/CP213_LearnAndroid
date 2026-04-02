package com.example.project.feature.decision.domain

data class Recommendation(
    val recommendedOptionId: String,
    val reasoning: String,
    val confidenceScore: Float,
    val prosAndCons: Map<String, ProsCons> = emptyMap()
)

data class ProsCons(
    val pros: List<String>,
    val cons: List<String>
)
