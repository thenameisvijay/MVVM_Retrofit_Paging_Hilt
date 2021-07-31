package com.vj.mvvm_retrofitpaginghilt.di

import com.squareup.moshi.Moshi
import com.vj.mvvm_retrofitpaginghilt.BuildConfig
import com.vj.mvvm_retrofitpaginghilt.helper.ArrayListMoshiAdapter
import com.vj.mvvm_retrofitpaginghilt.network.GithubEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * @Module informs Hilt how to provide instances of certain types. Unlike Dagger modules,
 * you must annotate Hilt modules with @InstallIn to tell Hilt which Android class each module will be used or installed in.
 *
 * Dependencies that you provide in Hilt modules are available in all generated components that are associated with the
 * Android class where you install the Hilt module.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesHttpLogger() = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesClient() = OkHttpClient.Builder().apply {
        this.addInterceptor(providesHttpLogger())
    }.build()

    @Provides
    @Singleton
    fun providesNetworkInstance(): GithubEndpoint {
        return Retrofit.Builder().run {
            baseUrl(BuildConfig.BASE_URL)
            client(providesClient())
            addConverterFactory(
                MoshiConverterFactory.create(
                Moshi
                    .Builder()
                    .add(ArrayListMoshiAdapter())
                    .build()
            )).build()
        }.create(GithubEndpoint::class.java)
    }
}