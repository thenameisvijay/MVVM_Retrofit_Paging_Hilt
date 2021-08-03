package com.vj.mvvm_retrofitpaginghilt.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingConfig.Companion.MAX_SIZE_UNBOUNDED
import androidx.paging.liveData
import com.vj.mvvm_retrofitpaginghilt.network.GithubEndpoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitRepository @Inject constructor(private val endpoint: GithubEndpoint) {

    fun callUserData(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = MAX_SIZE_UNBOUNDED,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { RepoPagingSource(endpoint, query) }
    ).liveData

    suspend fun callUserDetailsData(userName: String) = endpoint.requestUserDetailsData(userName)
}