
package com.vj.mvvm_retrofitpaginghilt.model

import com.vj.mvvm_retrofitpaginghilt.network.GithubEndpoint
import javax.inject.Inject

class GitRepository @Inject constructor(private val endpoint: GithubEndpoint) {
    suspend fun callUserData() = endpoint.requestRepoData()
    suspend fun callUserDetailsData(userName: String) = endpoint.requestUserDetailsData(userName)
}