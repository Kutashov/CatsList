package ru.alexandrkutashov.catslist.cats.all

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.alexandrkutashov.catslist.cats.data.local.CatsDatabase
import ru.alexandrkutashov.catslist.cats.data.local.FavoriteCat
import ru.alexandrkutashov.catslist.cats.data.remote.Cat
import ru.alexandrkutashov.catslist.cats.domain.CatsRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Alexandr Kutashov
 * on 22.01.2020
 */

private const val PAGE_SIZE = 20

@InjectViewState
class CatsPresenter @Inject constructor(
    private val catsRepository: CatsRepository,
    private val catsDb: CatsDatabase
) : MvpPresenter<CatsView>() {

    private var pageCount = -1
    private val disposable = CompositeDisposable()

    private val listUpateSubject = PublishSubject.create<List<Cat>>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        subscribeToUpdates()
        onLoadMore()
    }

    private fun subscribeToUpdates() {
        disposable.add(
            Observable.combineLatest(
                listUpateSubject.scan { oldList, newPage -> oldList + newPage },
                catsDb.catsDao().getFavorites().subscribeOn(Schedulers.io()),
                BiFunction<List<Cat>, List<FavoriteCat>, List<FavorableCat>> { catsList, favoriteList ->
                    catsList.map { cat ->
                        FavorableCat(cat, favoriteList.any { it.id == cat.id })
                    }
                }
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewState.showCats(it) }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    @Suppress("UnstableApiUsage")
    fun onLoadMore() {
        disposable.add(
            catsRepository.breeds(
                ++pageCount,
                PAGE_SIZE
            )
                .doOnSubscribe { viewState.showLoading(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { viewState.showLoading(false) }
                .subscribe(
                    { cats -> listUpateSubject.onNext(cats) },
                    { throwable -> Timber.d(throwable) }
                )
        )

        Timber.d("onLoadMore $pageCount")
    }

    fun addFavorite(cat: FavorableCat) {
        disposable.add(
            catsDb.catsDao().addFavorite(cat.toFavorite())
                .subscribeOn(Schedulers.io())
                .doOnError { throwable -> Timber.d(throwable) }
                .subscribe()
        )
    }

    private fun FavorableCat.toFavorite() = FavoriteCat(id, name, origin, infoUrl, imageUrl)
}