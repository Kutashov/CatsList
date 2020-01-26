package ru.alexandrkutashov.catslist.cats.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
@Database(entities = [FavoriteCat::class], version = 1)
abstract class CatsDatabase: RoomDatabase() {
    abstract fun catsDao(): CatsDao

    companion object {
        const val NAME = "cats-database"
    }
}