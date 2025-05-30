package ru.effectivem.a6_db_network_patterns.z3.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.effectivem.a6_db_network_patterns.z3.data.api.model.JokeResponse

interface ApiService {
    @GET("posts/1")
    suspend fun getPost(): Response<JokeResponse>
}