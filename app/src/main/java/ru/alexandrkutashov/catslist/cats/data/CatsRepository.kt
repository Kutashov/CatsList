package ru.alexandrkutashov.catslist.cats.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
class CatsRepository(private val catsApi: CatsApi) {

    fun breeds(page: Int, pageSize: Int): Single<List<Cat>> {
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