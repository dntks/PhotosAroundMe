package com.dtks.photosaroundme.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo ORDER BY createdTime DESC")
    fun observeAllTimeDesc(): Flow<List<PhotoEntity>>

    @Upsert
    suspend fun upsert(photoEntity: PhotoEntity)
    suspend fun insertWithTimestamp(photoEntity: PhotoEntity) {
        upsert(photoEntity.copy(
            createdTime = System.currentTimeMillis()
        ))
    }
}
