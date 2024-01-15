package com.dtks.photosaroundme.data.local

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface FailedPhotoDao {
    @Upsert
    suspend fun insert(photoEntity: FailedPhotoEntity)
    suspend fun insertWithTimestamp(failedPhotoEntity: FailedPhotoEntity) {
        insert(failedPhotoEntity.copy(
            createdTime = System.currentTimeMillis()
        ))
    }
}