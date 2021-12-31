package com.nsa.imagebrowsingapp.models

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.nsa.imagebrowsingapp.api.ImageApi
import retrofit2.HttpException
import java.io.IOException


private const val imageApi_starting_page_index=1
class ImagePagingSource(
    private val imageApi: ImageApi,
    private val query: String,
    private val totalResult: MutableLiveData<Int>,
    private val orientation: String?
): PagingSource<Int, image_model>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, image_model> {
        val position = params.key ?: imageApi_starting_page_index

       return try {
            val response = imageApi.searchPhotos(query, orientation.toString(),position, params.loadSize)
            val images = response.hits
           totalResult.value=response.total




//           val responseVideo = imageApi.searchVideos(query, position, params.loadSize)
//           val videos = responseVideo.hits




           LoadResult.Page(
               data = images,
               prevKey = if(position == imageApi_starting_page_index) null else position-1,
               nextKey = if(images.isEmpty()) null else position+1
           )
        }catch (exception:IOException){
            LoadResult.Error(exception)
        }catch (exception:HttpException){
        LoadResult.Error(exception)
    }
    }
}