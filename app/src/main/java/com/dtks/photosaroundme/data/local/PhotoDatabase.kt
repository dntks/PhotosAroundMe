package com.dtks.photosaroundme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PhotoEntity::class, FailedPhotoEntity::class], version = 2, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun failedPhotoDao(): FailedPhotoDao
}
