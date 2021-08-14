package com.maged.oldmovies.source.response.photoSize

import com.squareup.moshi.Json

/**
 * Used to handle API Response (target object, smallest building unit in json)
 */
data class PhotoSizeModel(
    @field:Json(name = "label") val label: String?,
    @field:Json(name = "source") val source: String?
)