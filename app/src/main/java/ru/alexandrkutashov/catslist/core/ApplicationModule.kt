package ru.alexandrkutashov.catslist.core

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Alexandr Kutashov
 * on 21.01.2020
 */
@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context
}