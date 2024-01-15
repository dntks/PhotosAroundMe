package com.dtks.photosaroundme.data.repository

import com.dtks.photosaroundme.data.RemoteDataSource
import com.dtks.photosaroundme.data.apimodel.SearchRequest
import com.dtks.photosaroundme.data.dafaultFailedPhotoEntity
import com.dtks.photosaroundme.data.defaultPhotoEntity
import com.dtks.photosaroundme.data.defaultPhotoResponse
import com.dtks.photosaroundme.data.defaultTestCoordinates
import com.dtks.photosaroundme.data.local.FailedPhotoDao
import com.dtks.photosaroundme.data.local.PhotoDao
import com.dtks.photosaroundme.data.photoItem1
import com.dtks.photosaroundme.data.photoItem2
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PhotoRepositoryTest {

    val remoteDataSourceMock = mockk<RemoteDataSource>()
    val photoDaoMock = mockk<PhotoDao>()
    val failedPhotoDaoMock = mockk<FailedPhotoDao>()
    private lateinit var repository: PhotoRepository
    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        coEvery { photoDaoMock.insertWithTimestamp(any()) } returns Unit
        coEvery { failedPhotoDaoMock.insertWithTimestamp(any()) } returns Unit
        repository = PhotoRepository(
            remoteDataSource = remoteDataSourceMock,
            photoDataSource = photoDaoMock,
            failedPhotoDao = failedPhotoDaoMock,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun searchPhotosMapsResponseCorrectly() = testScope.runTest {
        //one of these will be randomly picked
        val expextectItems = listOf(
            photoItem1, photoItem2
        )
        coEvery { remoteDataSourceMock.searchPhotos(any()) } returns defaultPhotoResponse
        val photoItem = repository.getRandomPhotoAt(
            SearchRequest(defaultTestCoordinates)
        )

        coVerify { remoteDataSourceMock.searchPhotos(defaultTestCoordinates) }

        Assert.assertTrue(
            expextectItems.contains(photoItem)
        )
    }
    @Test
    fun savePhotoCallsDaoCorrectly() = testScope.runTest {
        repository.savePhoto(photoItem1)

        coVerify { photoDaoMock.insertWithTimestamp(defaultPhotoEntity) }
    }
    @Test
    fun saveFailedPhotoCallsDaoCorrectly() = testScope.runTest {
        repository.saveFailedPhoto(defaultTestCoordinates, "test fail")

        coVerify { failedPhotoDaoMock.insertWithTimestamp(dafaultFailedPhotoEntity) }
    }
    @Test
    fun photoFlowMapsDaoFlowToPhotoItemsFlow() = testScope.runTest {
        every { photoDaoMock.observeAllTimeDesc() } returns flow {
            emit(listOf(defaultPhotoEntity))
        }
        val photoFlow = repository.getPhotoFlow()
        advanceUntilIdle()

        val photoItems = photoFlow.first()
        Assert.assertTrue(photoItems.size == 1)
        Assert.assertEquals(photoItem1, photoItems.first())
    }
}