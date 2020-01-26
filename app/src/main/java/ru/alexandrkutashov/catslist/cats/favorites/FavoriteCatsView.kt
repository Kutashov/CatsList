package ru.alexandrkutashov.catslist.cats.favorites

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.alexandrkutashov.catslist.cats.data.local.FavoriteCat

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
interface FavoriteCatsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCats(cats: List<FavoriteCat>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(isLoading: Boolean)
}