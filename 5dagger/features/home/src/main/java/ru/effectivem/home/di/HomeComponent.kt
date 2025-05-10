package ru.effectivem.home.di

import dagger.Component
import dagger.Module
import dagger.Provides
import ru.effectivem.api.network.NetworkDeps
import ru.effectivem.home.data.HomeRepository
import ru.effectivem.home.data.HomeRepositoryImpl
import ru.effectivem.home.ui.HomeFragment

@Component(modules = [HomeModule::class], dependencies = [NetworkDeps::class])
internal interface HomeComponent {

    fun inject(fragment: HomeFragment)
}

@Module
class HomeModule {

    @Provides
    fun bindHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository {
        return homeRepository
    }
}