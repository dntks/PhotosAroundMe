/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtks.photosaroundme.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the task table.
 */
@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo ORDER BY createdTime DESC")
    fun observeAll(): Flow<List<PhotoEntity>>

    @Upsert
    suspend fun upsert(photoEntity: PhotoEntity)
    suspend fun insertWithTimestamp(photoEntity: PhotoEntity) {
        upsert(photoEntity.copy(
            createdTime = System.currentTimeMillis()
        ))
    }
}
