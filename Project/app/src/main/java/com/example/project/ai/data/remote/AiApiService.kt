package com.example.project.ai.data.remote

data class AiRequest(
    val prompt: String,
    val model: String = "decidr-model"
)

data class AiResponse(
    val recommendedOptionId: String,
    val reasoning: String,
    val confidence: Float
)

interface AiApiService {
    // This is a generic endpoint acting as a placeholder for OpenAI, Gemini, etc.
    // Replace with correct path and annotations for Retrofit.
    suspend fun fetchRecommendation(request: AiRequest): AiResponse
}
