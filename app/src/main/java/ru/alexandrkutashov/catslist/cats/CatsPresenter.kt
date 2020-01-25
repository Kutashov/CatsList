package ru.alexandrkutashov.catslist.cats

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.alexandrkutashov.catslist.cats.data.CatsRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Alexandr Kutashov
 * on 22.01.2020
 */

private const val PAGE_SIZE = 20

@InjectViewState
class CatsPresenter @Inject constructor(private val catsRepository: CatsRepository) : MvpPresenter<CatsView>() {

    private var pageCount = -1
    private val disposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        onLoadMore()
    }

    @Suppress("UnstableApiUsage")
    fun onLoadMore() {
        viewState.showLoading(true)
        disposable.add(
            catsRepository.breeds(++pageCount, PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { viewState.showLoading(false) }
                .subscribe(
                    { cats -> viewState.showCats(cats) },
                    { throwable -> Timber.d(throwable) }
                )
        )

        Timber.d("onLoadMore $pageCount")
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}