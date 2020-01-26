package ru.alexandrkutashov.catslist.core.ui

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
interface ExternalStoragePermissionRequestingView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun requestStoragePermission()
}