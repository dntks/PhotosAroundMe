package com.dtks.photosaroundme.ui.overview.model

import com.dtks.photosaroundme.data.apimodel.Photo
import com.dtks.photosaroundme.data.local.PhotoEntity
import com.dtks.photosaroundme.utils.Coordinates

data class PhotoItem(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
    val coordinates: Coordinates,
    val createdTime: Long? = null
) {

    constructor(photo: Photo, coordinates: Coordinates) : this(
        photo.id,
        photo.owner,
        photo.secret,
        photo.server,
        photo.title,
        coordinates
    )

    constructor(photo: PhotoEntity) : this(
        photo.id,
        photo.owner,
        photo.secret,
        photo.server,
        photo.title,
        Coordinates(photo.latitude, photo.longitude),
        photo.createdTime
    )

    fun createImageUrl(): String {
        return BASE_URL + "${server}/${id}_${secret}.jpg"
    }

    companion object {
        private const val BASE_URL = "https://live.staticflickr.com/"
    }
}
