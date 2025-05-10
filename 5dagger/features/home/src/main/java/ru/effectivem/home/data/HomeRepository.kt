package ru.effectivem.home.data

import ru.effectivem.api.di.LocalHost
import ru.effectivem.api.di.MainHost
import ru.effectivem.api.network.ApiService
import javax.inject.Inject

interface HomeRepository {

    fun getFromMainHost(): String
    fun getFromLocalHost(): String
}

class HomeRepositoryImpl @Inject constructor(
    @MainHost private val mainHostService: ApiService,
    @LocalHost private val localHostService: ApiService,
) : HomeRepository {

    override fun getFromMainHost() = mainHostService.doSomething()
    override fun getFromLocalHost() = localHostService.doSomething()
}