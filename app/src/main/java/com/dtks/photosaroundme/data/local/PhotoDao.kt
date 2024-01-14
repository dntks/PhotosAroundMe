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

    /**
     * Observes list of tasks.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM photo")
    fun observeAll(): Flow<List<PhotoEntity>>

    /**
     * Observes a single task.
     *
     * @param photoId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM photo WHERE id = :photoId")
    fun observeById(photoId: String): Flow<PhotoEntity>

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM photo")
    suspend fun getAll(): List<PhotoEntity>

    /**
     * Select a task by id.
     *
     * @param photoId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM photo WHERE id = :photoId")
    suspend fun getById(photoId: String): PhotoEntity?

    /**
     * Insert or update a task in the database. If a task already exists, replace it.
     *
     * @param task the task to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(task: PhotoEntity)

    /**
     * Insert or update tasks in the database. If a task already exists, replace it.
     *
     * @param tasks the tasks to be inserted or updated.
     */
    @Upsert
    suspend fun upsertAll(tasks: List<PhotoEntity>)
}
