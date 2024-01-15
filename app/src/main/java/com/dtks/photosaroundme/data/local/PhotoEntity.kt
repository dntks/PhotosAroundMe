package com.dtks.photosaroundme.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dtks.photosaroundme.ui.photolist.model.PhotoItem

@Entity(
    tableName = "photo"
)
data class PhotoEntity(
    @PrimaryKey val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val createdTime: Long? = null
) {
    constructor(photo: PhotoItem) : this(
        photo.id,
        photo.owner,
        photo.secret,
        photo.server,
        photo.title,
        photo.coordinates.latitude,
        photo.coordinates.longitude
    )
}
