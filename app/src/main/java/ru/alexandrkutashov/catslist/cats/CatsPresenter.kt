package ru.alexandrkutashov.catslist.cats

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.alexandrkutashov.catslist.cats.data.CatsApi
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Alexandr Kutashov
 * on 22.01.2020
 */
@InjectViewState
class CatsPresenter @Inject constructor(private val catsApi: CatsApi) : MvpPresenter<CatsView>() {

    @Suppress("UnstableApiUsage")
    @SuppressLint("CheckResult")
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showLoading(true)
        catsApi.breeds(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { viewState.showLoading(false) }
            .subscribe(
                { cats -> viewState.showCats(cats) },
                { throwable -> Timber.d(throwable) }
            )
    }
}