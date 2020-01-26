package ru.alexandrkutashov.catslist.cats.di

import dagger.Component
import me.vponomarenko.injectionmanager.x.XInjectionManager
import retrofit2.Retrofit
import ru.alexandrkutashov.catslist.cats.all.CatsPresenter
import ru.alexandrkutashov.catslist.cats.data.local.CatsDatabase
import ru.alexandrkutashov.catslist.cats.favorites.FavoriteCatsPresenter
import ru.alexandrkutashov.catslist.core.util.DownloadHelper
import ru.alexandrkutashov.catslist.core.di.MainComponent
import javax.inject.Scope

/**
 * @author Alexandr Kutashov
 * on 24.01.2020
 */
@CatsListScope
@Component(modules = [CatsModule::class], dependencies = [MainComponent::class])
interface CatsComponent {

    val catsPresenter: CatsPresenter

    val favoriteCatsPresenter: FavoriteCatsPresenter

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
    val catsDatabase: CatsDatabase
    val downloadHelper: DownloadHelper
}