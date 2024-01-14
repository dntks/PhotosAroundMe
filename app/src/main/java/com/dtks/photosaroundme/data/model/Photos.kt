package com.dtks.photosaroundme.data.model

data class Photos(
    val page: Int,
    val pages: Int,
    val total: Int,
    val photo: List<Photo>
)
