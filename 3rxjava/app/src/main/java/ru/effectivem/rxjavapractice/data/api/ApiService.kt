package ru.effectivem.rxjavapractice.data.api

import io.reactivex.Single
import retrofit2.http.GET
import ru.effectivem.rxjavapractice.data.model.JokeResponse

interface ApiService {
    @GET("posts/1")
    fun getPost(): Single<JokeResponse>
}