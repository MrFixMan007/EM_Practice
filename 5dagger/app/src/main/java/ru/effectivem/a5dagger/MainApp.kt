package ru.effectivem.a5dagger

import android.app.Application
import ru.effectivem.a5dagger.di.AppComponent
import ru.effectivem.a5dagger.di.DaggerAppComponent
import ru.effectivem.home.ui.HomeDepsStore

class MainApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        HomeDepsStore.deps = DaggerAppComponent.create()
    }

}