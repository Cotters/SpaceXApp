package com.cotters.spacexapp.data

import com.squareup.moshi.Json

data class PaginationResponse<T>(
    @field:Json(name = "docs") val items: List<T?>?,
    @field:Json(name = "totalDocs") val totalItems: Int?,
    @field:Json(name = "limit") val limit: Int?,
    @field:Json(name = "totalPages") val totalPages: Int?,
    @field:Json(name = "page") val page: Int?,
    @field:Json(name = "pagingCounter") val pagingCounter: Int?,
    @field:Json(name = "hasPrevPage") val hasPrevPage: Boolean?,
    @field:Json(name = "hasNextPage") val hasNextPage: Boolean?,
    @field:Json(name = "prevPage") val prevPage: Int?,
    @field:Json(name = "nextPage") val nextPage: Int?,
)
