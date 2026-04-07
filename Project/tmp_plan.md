# Decidr AI Integration Plan

## Architecture: Google Gemini API via HTTP (Retrofit)

ใช้ Gemini 1.5 Flash ซึ่งฟรีและเร็ว
Endpoint: https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent

### Files to create/modify:
1. `GeminiApiService.kt` — Retrofit interface for Gemini
2. `GeminiModels.kt` — Request/Response DTOs
3. `GeminiAiChatRepository.kt` — Chat implementation
4. `GeminiRecommendationRepository.kt` — Pros/cons + recommendation
5. `DecidrNavGraph.kt` — Wire everything together
6. `AndroidManifest.xml` — INTERNET permission check
