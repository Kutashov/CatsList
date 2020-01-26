package ru.alexandrkutashov.catslist.cats.domain

import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.alexandrkutashov.catslist.RxSchedulerRule
import ru.alexandrkutashov.catslist.cats.data.remote.Cat
import ru.alexandrkutashov.catslist.cats.data.remote.CatImage
import ru.alexandrkutashov.catslist.cats.data.remote.CatsApi
import java.lang.IllegalStateException

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
class CatsRepositoryTest {

    @get:Rule
    val schedulerRule = RxSchedulerRule()

    private val catsApi = mock(CatsApi::class.java)
    private val repository = CatsRepositoryImpl(catsApi)

    @Test
    fun testPageDownloading() {
        val cat1 = Cat("1", "Barsik", "")
        val cat2 = Cat("2", "Venya", "")
        val cat3 = Cat("3", "Pushok", "")

        `when`(catsApi.breeds(0, 20)).thenReturn(Single.just(listOf(cat1, cat2, cat3)))
        `when`(catsApi.photo("1")).thenReturn(Single.just(listOf(CatImage("imageUrl1"))))
        `when`(catsApi.photo("2")).thenReturn(Single.just(listOf(CatImage("imageUrl2"))))
        `when`(catsApi.photo("3")).thenReturn(Single.error(IllegalStateException()))

        val expected = listOf(
            cat1.copy(imageUrl = "imageUrl1"),
            cat2.copy(imageUrl = "imageUrl2"),
            cat3
        )
        repository.breeds(0, 20).test().assertValueSet(setOf(expected))
    }
}