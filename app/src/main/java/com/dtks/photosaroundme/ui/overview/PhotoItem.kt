package com.dtks.photosaroundme.ui.overview

import com.dtks.photosaroundme.data.local.PhotoEntity
import com.dtks.photosaroundme.data.model.Photo

data class PhotoItem(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
){
    constructor(photo: Photo) : this(photo.id, photo.owner, photo.secret, photo.server, photo.title)
    constructor(photo: PhotoEntity) : this(photo.id, photo.owner, photo.secret, photo.server, photo.title)

    fun createImageUrl() : String{
        return BASE_URL + "${server}/${id}_${secret}.jpg"
    }

    companion object {
        private const val BASE_URL = "https://live.staticflickr.com/"
    }
}