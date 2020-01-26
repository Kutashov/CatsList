package ru.alexandrkutashov.catslist.cats.domain

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.alexandrkutashov.catslist.cats.data.remote.Cat
import ru.alexandrkutashov.catslist.cats.data.remote.CatsApi

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */

interface CatsRepository {
    fun breeds(page: Int, pageSize: Int): Single<List<Cat>>
}

class CatsRepositoryImpl(private val catsApi: CatsApi): CatsRepository {

    override fun breeds(page: Int, pageSize: Int): Single<List<Cat>> {
        return catsApi.breeds(page, pageSize)
            .flatMap { list ->
                Single.zip(
                    list.map { cat ->
                        catsApi.photo(cat.id).map { it.first() }
                            .map { cat.copy(imageUrl = it.url) }
                            .onErrorReturnItem(cat)
                    }
                ) { images -> images.map { it as Cat }.toList() }
            }.subscribeOn(Schedulers.io())
    }
}