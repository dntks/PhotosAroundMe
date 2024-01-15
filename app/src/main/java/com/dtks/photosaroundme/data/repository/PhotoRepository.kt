package com.dtks.photosaroundme.data.repository

import com.dtks.photosaroundme.data.RemoteDataSource
import com.dtks.photosaroundme.data.apimodel.SearchRequest
import com.dtks.photosaroundme.data.local.FailedPhotoDao
import com.dtks.photosaroundme.data.local.FailedPhotoEntity
import com.dtks.photosaroundme.data.local.PhotoDao
import com.dtks.photosaroundme.data.local.PhotoEntity
import com.dtks.photosaroundme.di.DefaultDispatcher
import com.dtks.photosaroundme.ui.overview.model.PhotoItem
import com.dtks.photosaroundme.utils.Coordinates
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
    private val photoDataSource: PhotoDao,
    private val failedPhotoDao: FailedPhotoDao,
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
        photoDataSource.insertWithTimestamp(PhotoEntity(photoItem))
    }

    suspend fun saveFailedPhoto(coordinates: Coordinates, cause: String) {
        failedPhotoDao.insertWithTimestamp(FailedPhotoEntity(
            latitude = coordinates.latitude,
            longitude = coordinates.longitude,
            cause = cause
        ))
    }

    fun getPhotoFlow(): Flow<List<PhotoItem>> {
        return photoDataSource.observeAllTimeDesc().map { photos ->
            withContext(dispatcher) {
                photos.map { PhotoItem(it) }
            }
        }
    }
}