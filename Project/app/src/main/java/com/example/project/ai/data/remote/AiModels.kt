package com.example.project.ai.data.remote

data class AiDecidrRequest(
    val question: String,
    val options: List<String>,
    val factors: List<String>
)

data class AiDecidrResponse(
    val recommendedOptionId: String,
    val explanation: String,
    val confidenceScore: Float,
    val optionsAnalysis: Map<String, OptionAnalysisResponse>
)

data class OptionAnalysisResponse(
    val pros: List<String>,
    val cons: List<String>
)
