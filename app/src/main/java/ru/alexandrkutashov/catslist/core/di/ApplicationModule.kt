package ru.alexandrkutashov.catslist.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.alexandrkutashov.catslist.cats.data.local.CatsDatabase
import ru.alexandrkutashov.catslist.core.util.DownloadHelper
import ru.alexandrkutashov.catslist.core.util.DownloadHelperImpl
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

    @Reusable
    @Provides
    fun downloadManager(context: Context): DownloadHelper = DownloadHelperImpl(context)
}