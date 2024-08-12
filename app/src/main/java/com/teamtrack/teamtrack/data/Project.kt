package com.teamtrack.teamtrack.data

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val id: Int,
    val projectName: String,
    val leaderId: String,
    val status: String,
    val startDate: String,
    val endDate: String,
    val createdAt: String,
    val updatedAt: String
)