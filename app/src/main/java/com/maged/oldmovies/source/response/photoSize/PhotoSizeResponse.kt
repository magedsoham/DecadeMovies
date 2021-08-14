package com.maged.oldmovies.source.response.photoSize

import com.squareup.moshi.Json

/**
 * Used to handle API Response (Root Response)
 */
data class PhotoSizeResponse(
    @field:Json(name = "sizes") val photoSizes: Sizes?
)