package com.nsa.imagebrowsingapp.adapters

import com.nsa.imagebrowsingapp.models.image_model

interface OnClickListener {
    fun onClick(imageModel: image_model)
}