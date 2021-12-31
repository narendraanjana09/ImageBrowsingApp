package com.nsa.imagebrowsingapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class image_model (
    val id:String,
    val user:String,
    val previewURL:String,
    val largeImageURL:String,
        ):Parcelable{

}