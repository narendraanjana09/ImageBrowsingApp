package com.nsa.imagebrowsingapp.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.nsa.imagebrowsingapp.api.ImageApi
import com.nsa.imagebrowsingapp.models.ImagePagingSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepository @Inject constructor(
    private val imageApi: ImageApi
    ) {
    fun getSearchResults(query: String, totalResult: MutableLiveData<Int>, orientation: String?)=
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {ImagePagingSource(imageApi,query,totalResult,orientation)}
        ).liveData
}