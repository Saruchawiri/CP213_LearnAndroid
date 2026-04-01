package com.example.project.ai.data

import com.example.project.ai.data.remote.AiApiService
import com.example.project.ai.data.remote.AiRequest
import com.example.project.ai.domain.AiRecommendationRepository
import com.example.project.core.error.AppError
import com.example.project.core.error.Resource
import com.example.project.feature.decision.domain.Decision
import com.example.project.feature.decision.domain.Recommendation

class NetworkAiRecommendationRepository(
    private val aiApiService: AiApiService
) : AiRecommendationRepository {

    override suspend fun getRecommendation(decision: Decision): Resource<Recommendation> {
        return try {
            // Build a structured prompt out of the user's decision
            val promptBuilder = StringBuilder("User query: ${decision.query}\n")
            /* 
               Add options and factors logic mapping here 
            */
            val promptText = promptBuilder.toString()

            val request = AiRequest(prompt = promptText)
            
            // Execute mock network call
            val response = aiApiService.fetchRecommendation(request)
            
            val recommendation = Recommendation(
                recommendedOptionId = response.recommendedOptionId,
                reasoning = response.reasoning,
                confidenceScore = response.confidence
            )
            
            Resource.Success(recommendation)
        } catch (e: Exception) {
            // In a real implementation you would catch HttpException, IOException separately
            Resource.Error(AppError.AiServiceUnavailable)
        }
    }
}
