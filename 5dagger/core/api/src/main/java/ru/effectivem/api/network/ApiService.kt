package ru.effectivem.api.network

import ru.effectivem.api.di.LocalHost
import ru.effectivem.api.di.MainHost

interface ApiService {

    fun doSomething(): String
}

interface NetworkDeps {

    @get:LocalHost
    val localHostService: ApiService

    @get:MainHost
    val mainHostService: ApiService

}