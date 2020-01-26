package ru.alexandrkutashov.catslist.cats.all

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import ru.alexandrkutashov.catslist.RxSchedulerRule
import ru.alexandrkutashov.catslist.cats.data.local.CatsDao
import ru.alexandrkutashov.catslist.cats.data.local.CatsDatabase
import ru.alexandrkutashov.catslist.cats.data.local.FavoriteCat
import ru.alexandrkutashov.catslist.cats.data.remote.Cat
import ru.alexandrkutashov.catslist.cats.domain.CatsRepository
import ru.alexandrkutashov.catslist.core.util.DownloadHelper

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
class CatsPresenterTest {

    @get:Rule
    val schedulerRule = RxSchedulerRule()

    private val catsRepository = mock(CatsRepository::class.java)
    private val database = mock(CatsDatabase::class.java)
    private val downloadHelper = mock(DownloadHelper::class.java)
    private lateinit var presenter: CatsPresenter

    private val catsView = mock(CatsView::class.java)
    private val catsViewState = mock(`CatsView$$State`::class.java)

    private val databaseSubject = BehaviorSubject.create<List<FavoriteCat>>()

    @Before
    fun setUp() {
        `when`(database.catsDao()).thenReturn(TestCatsDao(databaseSubject))
    }

    @Test
    fun testInitialShow() {

        val cat1 = Cat("1", "Barsik", "")
        val cat2 = Cat("2", "Venya", "")

        databaseSubject.onNext(listOf(FavoriteCat("1", "Barsik", "")))
        `when`(catsRepository.breeds(anyInt(), anyInt())).thenReturn(Single.just(
            listOf(cat1, cat2)
        ))
        initPresenter()

        verify(catsViewState).showLoading(true)
        verify(catsViewState).showLoading(false)
        verify(catsViewState).showCats(listOf(
            FavorableCat(cat1, true), FavorableCat(cat2, false)
        ))
    }

    @Test
    fun testLike() {
        val cat1 = Cat("1", "Barsik", "")

        databaseSubject.onNext(emptyList())
        `when`(catsRepository.breeds(anyInt(), anyInt())).thenReturn(Single.just(listOf(cat1)))
        initPresenter()

        verify(catsViewState).showCats(listOf(FavorableCat(cat1, false)))

        presenter.addFavorite(FavorableCat(cat1, false))

        verify(catsViewState).showCats(listOf(FavorableCat(cat1, true)))
    }

    @Test
    fun testDownload() {
        val cat = Cat("1", "Barsik", "", imageUrl = "someUrl")

        `when`(catsRepository.breeds(anyInt(), anyInt())).thenReturn(Single.just(listOf(cat)))
        initPresenter()

        presenter.downloadImage(FavorableCat(cat, true))
        verify(downloadHelper).download("someUrl")
    }

    private fun initPresenter() {
        presenter = CatsPresenter(catsRepository, database, downloadHelper)
        presenter.setViewState(catsViewState)
        presenter.attachView(catsView)
    }

    private class TestCatsDao(private val favoriteObservable: BehaviorSubject<List<FavoriteCat>>) : CatsDao {
        override fun getFavorites() = favoriteObservable
        override fun addFavorite(cat: FavoriteCat) = Completable.fromAction {
            favoriteObservable.onNext(favoriteObservable.value!! + cat)
        }
    }
}