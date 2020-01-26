package ru.alexandrkutashov.catslist.core.di

import android.content.Context
import dagger.Component
import ru.alexandrkutashov.catslist.cats.di.CatsDependencies
import javax.inject.Singleton

/**
 * @author Alexandr Kutashov
 * on 21.01.2020
 */
@Singleton
@Component(modules = [
    ApplicationModule::class,
    NetworkModule::class
])
interface MainComponent : CatsDependencies {

    class Initializer private constructor() {
        companion object {
            fun init(context: Context): MainComponent = DaggerMainComponent.builder()
                .applicationModule(
                    ApplicationModule(
                        context
                    )
                )
                .build()
        }
    }
}