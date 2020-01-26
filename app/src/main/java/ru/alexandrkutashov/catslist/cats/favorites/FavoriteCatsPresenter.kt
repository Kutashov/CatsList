package ru.alexandrkutashov.catslist.cats.favorites

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.alexandrkutashov.catslist.cats.data.local.CatsDatabase
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
@InjectViewState
class FavoriteCatsPresenter @Inject constructor(private val catsDb: CatsDatabase) :
    MvpPresenter<FavoriteCatsView>() {

    private val disposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showLoading(true)
        disposable.add(catsDb.catsDao().getFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Timber.d("Updating favorites ${it.joinToString()}") }
            .doOnNext { viewState.showLoading(it.isEmpty()) }
            .subscribe { viewState.showCats(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}