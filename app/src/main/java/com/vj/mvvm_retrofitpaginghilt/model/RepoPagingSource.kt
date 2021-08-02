package com.vj.mvvm_retrofitpaginghilt.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vj.mvvm_retrofitpaginghilt.model.data.Item
import com.vj.mvvm_retrofitpaginghilt.network.GithubEndpoint
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class RepoPagingSource(private val githubEndpoint: GithubEndpoint) :
    PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = githubEndpoint.requestRepoData(position, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition
    }

}