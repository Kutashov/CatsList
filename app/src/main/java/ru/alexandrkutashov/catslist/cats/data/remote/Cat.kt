package ru.alexandrkutashov.catslist.cats.data.remote

import com.google.gson.annotations.SerializedName
import ru.alexandrkutashov.catslist.cats.data.Meow

/**
 * @author Alexandr Kutashov
 * on 21.01.2020
 */
data class Cat(
    override val id: String,
    override val name: String,
    override val origin: String,
    @SerializedName("wikipedia_url")
    override val infoUrl: String?,
    override val imageUrl: String?
) : Meow

data class CatImage(
    val url: String
)