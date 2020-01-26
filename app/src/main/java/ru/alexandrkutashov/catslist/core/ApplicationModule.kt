package ru.alexandrkutashov.catslist.core

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.alexandrkutashov.catslist.cats.data.local.CatsDatabase
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

    @Singleton
    @Provides
    fun provideCatsDb(context: Context) =
        Room.databaseBuilder(
            context,
            CatsDatabase::class.java, CatsDatabase.NAME
        ).build()
}