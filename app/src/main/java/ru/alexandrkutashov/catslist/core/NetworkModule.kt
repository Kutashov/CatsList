package ru.alexandrkutashov.catslist.core

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexandrkutashov.catslist.BuildConfig
import timber.log.Timber
import javax.inject.Singleton

/**
 * @author Alexandr Kutashov
 * on 21.01.2020
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .addNetworkInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("x-api-key", BuildConfig.API_KEY)
                    .build()
                chain.proceed(newRequest)
            }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}