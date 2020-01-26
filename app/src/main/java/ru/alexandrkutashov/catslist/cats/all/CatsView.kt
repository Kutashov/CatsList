package ru.alexandrkutashov.catslist.cats.all

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.alexandrkutashov.catslist.core.ui.ExternalStoragePermissionRequestingView

/**
 * @author Alexandr Kutashov
 * on 22.01.2020
 */
interface CatsView: MvpView,
    ExternalStoragePermissionRequestingView {

    @StateStrategyType(AddToEndStrategy::class)
    fun showCats(cats: List<FavorableCat>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(isLoading: Boolean)
}