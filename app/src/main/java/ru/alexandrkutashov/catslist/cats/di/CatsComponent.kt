package ru.alexandrkutashov.catslist.cats.di

import dagger.Component
import me.vponomarenko.injectionmanager.x.XInjectionManager
import retrofit2.Retrofit
import ru.alexandrkutashov.catslist.cats.CatsPresenter
import ru.alexandrkutashov.catslist.core.MainComponent
import javax.inject.Scope

/**
 * @author Alexandr Kutashov
 * on 24.01.2020
 */
@CatsListScope
@Component(modules = [CatsModule::class], dependencies = [MainComponent::class])
interface CatsComponent {

    val presenter: CatsPresenter

    class Initializer private constructor() {
        companion object {
            fun init(): CatsComponent =
                DaggerCatsComponent.builder()
                    .mainComponent(XInjectionManager.findComponent())
                    .build()
        }
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CatsListScope

interface CatsDependencies {

    val retrofit: Retrofit
}