package ru.effectivem.api.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainHost

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalHost