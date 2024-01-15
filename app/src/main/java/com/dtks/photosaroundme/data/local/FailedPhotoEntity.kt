package com.dtks.photosaroundme.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "failedphoto"
)
data class FailedPhotoEntity (
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val latitude: Double,
    val longitude: Double,
    val cause: String,
    val createdTime: Long? = null
)