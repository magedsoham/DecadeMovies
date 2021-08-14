package com.maged.oldmovies.source.response.photoSize

import com.squareup.moshi.Json

/**
 * Used to handle API Response
 */
data class Sizes(
    @field:Json(name = "size") val sizes: List<PhotoSizeModel>?
) {
    fun getUrl(): String {
        val photoSizeModel = sizes?.find { model -> model.label == "Large Square" }
        return photoSizeModel?.source ?: ""
    }
}