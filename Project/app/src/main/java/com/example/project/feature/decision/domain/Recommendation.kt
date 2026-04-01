package com.example.project.feature.decision.domain

data class Recommendation(
    val recommendedOptionId: String,
    val reasoning: String,
    val confidenceScore: Float
)
