package ru.alexandrkutashov.catslist.cats.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
@Entity
data class FavoriteCat(
    @PrimaryKey
    val id: String,
    val name: String,
    val origin: String,
    @ColumnInfo(name = "info_url")
    val infoUrl: String?,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?
)