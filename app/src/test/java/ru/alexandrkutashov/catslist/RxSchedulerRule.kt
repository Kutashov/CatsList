package ru.alexandrkutashov.catslist

import io.reactivex.Scheduler
import io.reactivex.functions.Function
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable


/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
class RxSchedulerRule : TestRule {

    private val schedulerInstance = Schedulers.trampoline()
    private val schedulerFunction: Function<Scheduler, Scheduler> = Function { schedulerInstance }
    private val schedulerFunctionLazy: Function<Callable<Scheduler>, Scheduler> = Function { schedulerInstance }

    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerFunctionLazy)
                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(schedulerFunction)
                RxJavaPlugins.setNewThreadSchedulerHandler(schedulerFunction)
                RxJavaPlugins.setComputationSchedulerHandler(schedulerFunction)
                base.evaluate()
                RxAndroidPlugins.reset()
                RxJavaPlugins.reset()
            }
        }
    }
}