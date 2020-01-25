package ru.alexandrkutashov.catslist.cats.data

import com.google.gson.annotations.SerializedName

/**
 * @author Alexandr Kutashov
 * on 21.01.2020
 */
data class Cat(
    val id: String,
    val name: String,
    val origin: String,
    @SerializedName("wikipedia_url")
    val infoUrl: String
)