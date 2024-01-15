package com.dtks.photosaroundme.data

import com.dtks.photosaroundme.data.apimodel.Photo
import com.dtks.photosaroundme.data.apimodel.Photos
import com.dtks.photosaroundme.data.apimodel.SearchResponse
import com.dtks.photosaroundme.data.local.FailedPhotoEntity
import com.dtks.photosaroundme.data.local.PhotoEntity
import com.dtks.photosaroundme.ui.photolist.PhotoListUiState
import com.dtks.photosaroundme.ui.photolist.model.PhotoItem
import com.dtks.photosaroundme.utils.Coordinates

val photo1 = Photo(
    id = "Jim",
    title = "Regional manager",
    owner = "Ricky Gervais",
    server = "666",
    secret = "s4cr4t",
)
val photo2 = Photo(
    id = "Dwight",
    title = "Assistant to the Regional manager",
    owner = "Ricky Gervais",
    server = "666",
    secret = "s4cr4t",
)
val defaultPhotoResponse = SearchResponse(
    photos = Photos(
        photo = listOf(
            photo1, photo2
        ),
        page = 1,
        pages = 20,
        total = 131
    ),
    stat = "stat"
)
val defaultTestCoordinates = Coordinates(52.3676, 4.9041)
val photoItem1 = PhotoItem(
    id = "Jim",
    title = "Regional manager",
    owner = "Ricky Gervais",
    server = "666",
    secret = "s4cr4t",
    coordinates = defaultTestCoordinates
)
val photoItem2 = PhotoItem(
    id = "Dwight",
    title = "Assistant to the Regional manager",
    owner = "Ricky Gervais",
    server = "666",
    secret = "s4cr4t",
    coordinates = defaultTestCoordinates
)
val photoListUiStateWithItems = PhotoListUiState(
    isEmpty = false,
    isLoading = false,
    items = listOf(
        photoItem1, photoItem2
    )
)
val photoListUiStateEmptyLoading = PhotoListUiState(
    isEmpty = true,
    isLoading = true,
    items = listOf()
)
val photoListUiStateEmptyNotLoading = PhotoListUiState(
    isEmpty = true,
    isLoading = false,
    items = listOf()
)
val defaultPhotoEntity = PhotoEntity(photoItem1)
val dafaultFailedPhotoEntity = FailedPhotoEntity(
    latitude = defaultTestCoordinates.latitude,
    longitude = defaultTestCoordinates.longitude,
    cause = "test fail"
)