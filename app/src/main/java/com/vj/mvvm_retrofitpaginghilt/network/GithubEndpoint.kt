package com.vj.mvvm_retrofitpaginghilt.network

import com.vj.mvvm_retrofitpaginghilt.BuildConfig
import com.vj.mvvm_retrofitpaginghilt.model.RepoResponse
import com.vj.mvvm_retrofitpaginghilt.model.UserDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubEndpoint {
    @GET(BuildConfig.SUB_URL_HEAD + BuildConfig.SUB_URL_TAIL)
    suspend fun requestRepoData(): ArrayList<RepoResponse>

    @GET("users/{userName}")
    suspend fun requestUserDetailsData(@Path("userName") userName:String): UserDetailsResponse
}