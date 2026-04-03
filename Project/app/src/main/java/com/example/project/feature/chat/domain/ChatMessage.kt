package com.example.project.feature.chat.domain

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromJelly: Boolean
)
