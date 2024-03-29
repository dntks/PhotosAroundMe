/*
 * Copyright 2022 The Android Open Source Project
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

package com.dtks.photosaroundme.di

import android.content.Context
import androidx.room.Room
import com.dtks.photosaroundme.data.local.FailedPhotoDao
import com.dtks.photosaroundme.data.local.PhotoDao
import com.dtks.photosaroundme.data.local.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PhotoDatabase::class.java,
            "Photos.db"
        ).build()
    }

    @Provides
    fun providePhotoDao(database: PhotoDatabase): PhotoDao = database.photoDao()
    @Provides
    fun provideFailedPhotoDao(database: PhotoDatabase): FailedPhotoDao = database.failedPhotoDao()
}
