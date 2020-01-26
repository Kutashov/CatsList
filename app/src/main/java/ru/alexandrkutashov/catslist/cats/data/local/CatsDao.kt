package ru.alexandrkutashov.catslist.cats.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
@Dao
interface CatsDao {

    @Query("SELECT * FROM favoritecat")
    fun getFavorites(): Observable<List<FavoriteCat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(cat: FavoriteCat): Completable
}