package ru.effectivem.rxjavapractice.data.model

data class JokeResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)
