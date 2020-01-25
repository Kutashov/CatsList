package ru.alexandrkutashov.catslist.cats

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.alexandrkutashov.catslist.cats.data.Cat

/**
 * @author Alexandr Kutashov
 * on 22.01.2020
 */
interface CatsView: MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun showCats(cats: List<Cat>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(isLoading: Boolean)
}