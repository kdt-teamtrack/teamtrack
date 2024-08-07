package com.teamtrack.teamtrack.data

data class Task(
    val id: Int,
    val assignedUser: String,
    val taskName: String,
    val isAssignedByLeader: Boolean,
    val taskStatus: String,
    val createdAt: String,
    val updatedAt: String
)
