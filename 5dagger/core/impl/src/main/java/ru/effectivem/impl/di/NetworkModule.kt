package ru.effectivem.impl.di

import dagger.Module
import dagger.Provides
import ru.effectivem.api.di.LocalHost
import ru.effectivem.api.di.MainHost
import ru.effectivem.api.network.ApiService
import ru.effectivem.impl.network.LocalHostService
import ru.effectivem.impl.network.MainHostService

@Module
class NetworkModule {

    @Provides
    @MainHost
    fun provideMainHostService(): ApiService {
        return MainHostService() // т.к. Retrofit создаётся через билдер, то Binds нельзя
    }

    @Provides
    @LocalHost
    fun provideLocalHostService(): ApiService {
        return LocalHostService() // т.к. Retrofit создаётся через билдер, то Binds нельзя
    }

}