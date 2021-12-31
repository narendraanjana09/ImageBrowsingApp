package com.nsa.imagebrowsingapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class combine_model (
   val data:String,
   val type:Int
        ):Parcelable{

}