package com.dtks.photosaroundme.data.repository

import com.dtks.photosaroundme.data.RemoteDataSource
import com.dtks.photosaroundme.data.local.PhotoDao
import com.dtks.photosaroundme.data.local.PhotoEntity
import com.dtks.photosaroundme.data.model.SearchRequest
import com.dtks.photosaroundme.di.DefaultDispatcher
import com.dtks.photosaroundme.ui.overview.PhotoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: PhotoDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
){
    suspend fun getRandomPhotoAt(searchRequest: SearchRequest): PhotoItem? {
        return withContext(dispatcher) {
            val searchPhotos = remoteDataSource.searchPhotos(searchRequest.coordinates)
            searchPhotos.photos.photo.randomOrNull()?.let {
                PhotoItem(it, searchRequest.coordinates)
            }
        }
    }
    fun firstPhotoResponseFlow(searchRequest: SearchRequest): Flow<PhotoItem?> = flow {
        emit(getRandomPhotoAt(searchRequest))
    }

    suspend fun savePhoto(photoItem: PhotoItem) {
        localDataSource.insertWithTimestamp(PhotoEntity(photoItem))
    }

    fun getPhotoFlow(): Flow<List<PhotoItem>> {
        return localDataSource.observeAll().map { photos ->
            withContext(dispatcher) {
                photos.map { PhotoItem(it) }
            }
        }
    }
}