package com.dtks.photosaroundme.data

import com.dtks.photosaroundme.data.api.FlickrApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class RemoteDataSourceTest {
    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)

    val flickrApi: FlickrApi = mockk()
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        coEvery { flickrApi.searchImagesAtCoordinates(latitude = any(), longitude = any()) } returns
                defaultPhotoResponse
        remoteDataSource = RemoteDataSource(
            flickrApi
        )
    }

    @Test
    fun searchPhotosCallsFlickrApiAndReturnsCorrectly() = testScope.runTest {
        val searchPhotosResponse = remoteDataSource.searchPhotos(defaultTestCoordinates)
        coVerify {
            flickrApi.searchImagesAtCoordinates(
                latitude = defaultTestCoordinates.latitude,
                longitude = defaultTestCoordinates.longitude
            )
        }
        Assert.assertEquals(defaultPhotoResponse, searchPhotosResponse)
    }
}