package com.vj.mvvm_retrofitpaginghilt.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.vj.mvvm_retrofitpaginghilt.BuildConfig
import com.vj.mvvm_retrofitpaginghilt.model.GitRepository
import com.vj.mvvm_retrofitpaginghilt.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(private val gitRepository: GitRepository) : ViewModel() {

    private val repoQuery = MutableLiveData(BuildConfig.SUB_URL_TAIL)

    val repoList = repoQuery.switchMap {
        gitRepository.callUserData(BuildConfig.SUB_URL_TAIL).cachedIn(viewModelScope)
    }

    fun getUserDetails(userName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = gitRepository.callUserDetailsData(userName)))

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}