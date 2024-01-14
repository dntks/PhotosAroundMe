package com.dtks.photosaroundme.data.model

data class Photo (
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
)
