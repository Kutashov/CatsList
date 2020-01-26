package ru.alexandrkutashov.catslist.cats.all

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author Alexandr Kutashov
 * on 22.01.2020
 */
interface CatsView: MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun showCats(cats: List<FavorableCat>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(isLoading: Boolean)
}