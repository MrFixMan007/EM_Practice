package ru.effectivem.impl.network

import ru.effectivem.api.network.ApiService

class LocalHostService : ApiService {

    override fun doSomething() = "LocalHost"
}