package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project.ai.data.NetworkAiRecommendationRepository
import com.example.project.ai.data.remote.AiApiService
import com.example.project.ai.data.remote.AiRequest
import com.example.project.ai.data.remote.AiResponse
import com.example.project.app.navigation.DecidrNavGraph
import com.example.project.feature.decision.data.OfflineFirstDecisionRepository
import com.example.project.feature.decision.data.local.DecisionDao
import com.example.project.feature.decision.data.local.DecisionEntity
import com.example.project.feature.decision.ui.DecisionProcessViewModel
import com.example.project.ui.theme.ProjectTheme
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MainActivity : ComponentActivity() {

    // Simple manual DI for demonstration without Hilt/Koin
    private val viewModel by viewModels<DecisionProcessViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Mock dependencies for compilation
                val mockDao = object : DecisionDao {
                    override suspend fun insertDecision(decisionEntity: DecisionEntity) {}
                    override fun getDecisionById(decisionId: String): Flow<DecisionEntity?> = emptyFlow()
                    override fun getAllDecisions(): Flow<List<DecisionEntity>> = emptyFlow()
                    override suspend fun deleteDecisionById(decisionId: String) {}
                }
                
                val mockAiService = object : AiApiService {
                    override suspend fun fetchRecommendation(request: AiRequest): AiResponse {
                        return AiResponse("opt_1", "Mock Reason", 0.95f)
                    }
                }

                val decisionRepo = OfflineFirstDecisionRepository(mockDao, Gson())
                val aiRepo = NetworkAiRecommendationRepository(mockAiService)
                
                @Suppress("UNCHECKED_CAST")
                return DecisionProcessViewModel(decisionRepo, aiRepo) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DecidrNavGraph(decisionViewModel = viewModel)
                }
            }
        }
    }
}