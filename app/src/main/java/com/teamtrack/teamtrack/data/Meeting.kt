package com.teamtrack.teamtrack.data

import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val id: Int,
    val meetingDate: String,
    val agenda: String,
    val content: String,
    val summary: String,
    val attendees: String,
    val isContentVisible: Boolean,
    val createdAt: String,
    val updatedAt: String
)
