package ru.alexandrkutashov.catslist

import android.app.Application
import me.vponomarenko.injectionmanager.IHasComponent
import me.vponomarenko.injectionmanager.x.XInjectionManager
import ru.alexandrkutashov.catslist.core.di.MainComponent
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * @author Alexandr Kutashov
 * on 21.01.2020
 */
class App: Application(), IHasComponent<MainComponent> {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        XInjectionManager.init(this)
        XInjectionManager.bindComponent(this)
    }

    override fun getComponent() = MainComponent.Initializer.init(this)
}