package com.dtks.photosaroundme.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dtks.photosaroundme.ui.overview.PhotoItem

@Entity(
    tableName = "photo"
)
data class PhotoEntity(
    @PrimaryKey val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
){
    constructor(photo: PhotoItem) : this(photo.id, photo.owner, photo.secret, photo.server, photo.title)

}
