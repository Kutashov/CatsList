package ru.alexandrkutashov.catslist.cats.data

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
interface Meow {
    val id: String
    val name: String
    val origin: String
    val imageUrl: String?
    val infoUrl: String?
}