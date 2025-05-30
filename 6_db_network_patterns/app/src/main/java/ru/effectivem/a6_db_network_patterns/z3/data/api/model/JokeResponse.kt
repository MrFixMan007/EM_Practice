package ru.effectivem.a6_db_network_patterns.z3.data.api.model

data class JokeResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)
