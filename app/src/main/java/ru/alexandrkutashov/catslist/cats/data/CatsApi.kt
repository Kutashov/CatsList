package ru.alexandrkutashov.catslist.cats.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @author Alexandr Kutashov
 * on 21.01.2020
 */
interface CatsApi {

    @GET("breeds")
    fun breeds(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<List<Cat>>
}