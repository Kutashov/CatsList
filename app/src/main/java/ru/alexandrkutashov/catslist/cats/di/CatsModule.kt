package ru.alexandrkutashov.catslist.cats.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import ru.alexandrkutashov.catslist.cats.data.CatsApi

/**
 * @author Alexandr Kutashov
 * on 21.01.2020
 */
@Module
class CatsModule {

    @CatsListScope
    @Provides
    fun provideCatsApi(retrofit: Retrofit): CatsApi {
        return retrofit.create(CatsApi::class.java)
    }
}