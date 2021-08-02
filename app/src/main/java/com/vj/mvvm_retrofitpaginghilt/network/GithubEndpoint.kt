package com.vj.mvvm_retrofitpaginghilt.network

import com.vj.mvvm_retrofitpaginghilt.BuildConfig
import com.vj.mvvm_retrofitpaginghilt.model.UserDetailsResponse
import com.vj.mvvm_retrofitpaginghilt.model.data.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubEndpoint {
    @GET(BuildConfig.SUB_URL_HEAD)
    suspend fun requestRepoData(
        @Query("q") query: String,
        @Query("position") position: Int,
        @Query("per_page") loadSize: Int
    ): RepoResponse

    @GET("users/{userName}")
    suspend fun requestUserDetailsData(@Path("userName") userName: String): UserDetailsResponse
}