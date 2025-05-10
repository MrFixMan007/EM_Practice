package ru.effectivem.a5dagger.di

import dagger.Component
import ru.effectivem.api.network.NetworkDeps
import ru.effectivem.impl.di.NetworkModule
import javax.inject.Scope

@Scope
annotation class AppScope

@AppScope
@Component(modules = [NetworkModule::class])
interface AppComponent : NetworkDeps