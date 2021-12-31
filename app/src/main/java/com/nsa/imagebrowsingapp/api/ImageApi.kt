package com.nsa.imagebrowsingapp.api


import android.graphics.drawable.GradientDrawable
import com.nsa.imagebrowsingapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageApi {

    companion object{
        const val base_url="https://pixabay.com/"
         const val api_key=BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @GET("api/?key=$api_key&safesearch=true")
    suspend fun searchPhotos(
        @Query("q") query: String,
        @Query("orientation") orientation: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ):ApiResponse

    @GET("api/videos/?key=$api_key")
    suspend fun searchVideos(
        @Query("q") query: String,
        @Query("page") page:Int,
        @Query("per_page") perPage:Int
    ):ApiResponseVideo
}