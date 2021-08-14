package com.maged.oldmovies.source.response.photoSearch

import com.squareup.moshi.Json

/**
 * Used to handle API Response (target object, smallest building unit in json)
 */
data class PhotoSearchModel(
    @field:Json(name = "id") val id: String?
)