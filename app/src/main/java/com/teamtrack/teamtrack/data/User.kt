package com.teamtrack.teamtrack.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val userName: String,
    val teamName: String,
    val rank: String,
    val auth: String,
    val email: String,
    val phoneNumber: String,
    val createdAt: String,
    val updatedAt: String,
    val state: String,
    val profileImageUrl: String
)