package com.maged.oldmovies.source.response.photoSearch

import com.squareup.moshi.Json

/**
 * Used to handle API Response (Root Response)
 */
data class PhotosSearchResponse(
    @field:Json(name = "photos") val photosRoot: Photos?
)