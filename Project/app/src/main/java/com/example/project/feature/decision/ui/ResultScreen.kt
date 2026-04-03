package com.example.project.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.project.feature.decision.domain.Option
import com.example.project.feature.decision.domain.ProsCons
import com.example.project.feature.decision.domain.Recommendation
import com.example.project.ui.components.JellyMessageBubble

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    onBack: () -> Unit,
    // Mocked default for seamless preview and transition before viewmodel integration
    recommendation: Recommendation? = Recommendation(
        recommendedOptionId = "opt-1",
        reasoning = "Noodles offer the best balance of delicious taste and affordability! Plus, it's super comforting! 🍜",
        confidenceScore = 0.85f,
        prosAndCons = mapOf(
            "opt-1" to ProsCons(score = 92, pros = listOf("Budget-friendly", "Very tasty"), cons = listOf("Might be hot today")),
            "opt-2" to ProsCons(score = 78, pros = listOf("Incredible taste", "Healthy"), cons = listOf("A bit expensive"))
        )
    ),
    options: List<Option> = listOf(
        Option("opt-1", "Noodles", "Yummy bowl of ramen"),
        Option("opt-2", "Sushi", "Fresh salmon rolls")
    ),
    onFeedback: (Boolean) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var feedbackState by remember { mutableStateOf<Boolean?>(null) }
    var showThanks by remember { mutableStateOf(false) }

    if (recommendation == null || options.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Jelly is thinking...")
            Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) { Text("Go back") }
        }
        return
    }

    val bestOption = options.find { it.id == recommendation.recommendedOptionId } ?: options.first()
    val confPercent = (recommendation.confidenceScore * 100).toInt()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Decision Result") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Jelly Reaction
            JellyMessageBubble(
                message = "Ta-da! Based on what's important to you, here's my top pick! 🐾",
                isJellySpeaking = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Best Option Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Winner",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Winner!",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    Text(
                        text = bestOption.title,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                    if (bestOption.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = bestOption.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // AI Explanation Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Jelly's Reasoning",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = recommendation.reasoning,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Confidence Score
            Text(
                text = "Confidence Score",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                var progress by remember { mutableFloatStateOf(0f) }
                LaunchedEffect(recommendation.confidenceScore) {
                    progress = recommendation.confidenceScore
                }
                val animatedProgress by animateFloatAsState(
                    targetValue = progress, 
                    label = "confidence_anim", 
                    animationSpec = tween(1500)
                )

                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "$confPercent%",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Score Chart Visualization
            Text(
                text = "Score Analysis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val maxScore = recommendation.prosAndCons.values.maxOfOrNull { it.score }?.coerceAtLeast(1) ?: 100
                    
                    options.forEach { option ->
                        val optionData = recommendation.prosAndCons[option.id]
                        val score = optionData?.score ?: 0
                        val targetProgress = score.toFloat() / 100f
                        
                        var currentProgress by remember { mutableFloatStateOf(0f) }
                        LaunchedEffect(targetProgress) {
                            currentProgress = targetProgress
                        }
                        
                        val animatedProgress by animateFloatAsState(
                            targetValue = currentProgress,
                            animationSpec = tween(durationMillis = 1000, delayMillis = 200),
                            label = "chart_anim"
                        )
                        
                        // Pastel colors based on rank
                        val isWinner = option.id == recommendation.recommendedOptionId
                        val barColor = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
                        
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = option.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
                                )
                                Text(
                                    text = "$score",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = barColor
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(animatedProgress)
                                        .fillMaxHeight()
                                        .background(barColor)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Score Chart Visualization
            Text(
                text = "Score Analysis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val maxScore = recommendation.prosAndCons.values.maxOfOrNull { it.score }?.coerceAtLeast(1) ?: 100
                    
                    options.forEach { option ->
                        val optionData = recommendation.prosAndCons[option.id]
                        val score = optionData?.score ?: 0
                        val targetProgress = score.toFloat() / 100f
                        
                        var currentProgress by remember { mutableFloatStateOf(0f) }
                        LaunchedEffect(targetProgress) {
                            currentProgress = targetProgress
                        }
                        
                        val animatedProgress by animateFloatAsState(
                            targetValue = currentProgress,
                            animationSpec = tween(durationMillis = 1000, delayMillis = 200),
                            label = "chart_anim"
                        )
                        
                        // Pastel colors based on rank
                        val isWinner = option.id == recommendation.recommendedOptionId
                        val barColor = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
                        
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = option.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
                                )
                                Text(
                                    text = "$score",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = barColor
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(animatedProgress)
                                        .fillMaxHeight()
                                        .background(barColor)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // All Scores (Pros & Cons)
            Text(
                text = "Option Breakdown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            recommendation.prosAndCons.forEach { (optionId, prosCons) ->
                val option = options.find { it.id == optionId }
                if (option != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = option.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            if (prosCons.pros.isNotEmpty()) {
                                Text("Pros:", fontWeight = FontWeight.SemiBold, color = Color(0xFF4CAF50), modifier = Modifier.padding(bottom = 4.dp))
                                prosCons.pros.forEach { pro -> Text("• $pro", style = MaterialTheme.typography.bodyMedium) }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            if (prosCons.cons.isNotEmpty()) {
                                Text("Cons:", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 4.dp))
                                prosCons.cons.forEach { con -> Text("• $con", style = MaterialTheme.typography.bodyMedium) }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Feedback Section
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Was this helpful?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                feedbackState = true
                                showThanks = true
                                onFeedback(true)
                            },
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    color = if (feedbackState == true) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                imageVector = if (feedbackState == true) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                contentDescription = "Like",
                                tint = if (feedbackState == true) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(24.dp))
                        
                        IconButton(
                            onClick = {
                                feedbackState = false
                                showThanks = true
                                onFeedback(false)
                            },
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    color = if (feedbackState == false) MaterialTheme.colorScheme.error else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                imageVector = if (feedbackState == false) Icons.Filled.ThumbDown else Icons.Outlined.ThumbDown,
                                contentDescription = "Dislike",
                                tint = if (feedbackState == false) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    
                    if (showThanks) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Thanks for the feedback! 🐾",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}