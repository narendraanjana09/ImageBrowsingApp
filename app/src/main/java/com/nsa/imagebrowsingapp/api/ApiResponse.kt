package com.nsa.imagebrowsingapp.api

import com.nsa.imagebrowsingapp.models.image_model
import com.nsa.imagebrowsingapp.models.video_model

data class ApiResponse (
    val hits:List<image_model>,
    val total:Int
        )