package com.nsa.imagebrowsingapp.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.nsa.imagebrowsingapp.repo.MainRepository

class MainViewModel @ViewModelInject constructor(
    private val repo: MainRepository,
    @Assisted state:SavedStateHandle
    ):ViewModel() {

    val currentQuery = state.getLiveData(current_query,default_query)

    val totalResult=MutableLiveData<Int>()
    val orientation= MutableLiveData<String>()



    val images =currentQuery.switchMap { queryString->

            repo.getSearchResults(queryString,totalResult,orientation.value).cachedIn(viewModelScope)
    }

    fun searchImage(query:String){
        currentQuery.value = query
    }

    companion object{
        private const val current_query = "current_query"
        private const val default_query = ""
    }
}