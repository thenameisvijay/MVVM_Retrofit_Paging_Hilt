
package com.vj.mvvm_retrofitpaginghilt.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vj.mvvm_retrofitpaginghilt.network.GithubEndpoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitRepository @Inject constructor(private val endpoint: GithubEndpoint) {
    suspend fun callUserData() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 60,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {RepoPagingSource(endpoint)}
    ).liveData

    suspend fun callUserDetailsData(userName: String) = endpoint.requestUserDetailsData(userName)
}