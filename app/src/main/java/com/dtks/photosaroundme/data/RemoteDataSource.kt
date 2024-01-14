package com.dtks.photosaroundme.data

import com.dtks.photosaroundme.data.api.FlickrApi
import com.dtks.photosaroundme.data.model.SearchResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val flickrApi: FlickrApi
) {
    suspend fun searchPhotos(coordinates: Coordinates): SearchResponse {
        return flickrApi.searchImagesAtCoordinates(
            latitude = coordinates.latitude,
            longitude = coordinates.longitude
        )
    }
}