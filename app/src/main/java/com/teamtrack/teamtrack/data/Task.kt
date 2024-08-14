package com.teamtrack.teamtrack.data

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val projectId: Int,
    val assignedUser: String,
    val taskName: String,
    val isAssignedByLeader: Boolean,
    val taskStatus: String,
    val createdAt: String,
    val updatedAt: String
)
