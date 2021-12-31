package com.nsa.imagebrowsingapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class video_model (
    val id:String,
    val tags:String,
    val views:Int,
    val user:String,
    val userImageURL:String,
    val duration:Int,
    val videos:videos_
    ):Parcelable{

    @Parcelize
    data class videos_(
        val large:video_data,
        val medium:video_data,
        val small:video_data
    ):Parcelable{}

    @Parcelize
    data class video_data(
        val url:String,
        val width:Int,
        val height:Int,
        val size:Int
    ):Parcelable{}

}