package com.dtks.photosaroundme.viewmodel

import com.dtks.photosaroundme.MainCoroutineRule
import com.dtks.photosaroundme.data.photoItem1
import com.dtks.photosaroundme.data.repository.PhotoRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class PhotoViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var photoViewModel: PhotoViewModel
    val repositoryMock: PhotoRepository = mockk()

    @Before
    fun setup() {
        every { repositoryMock.getPhotoFlow() } returns flow {
            emit(listOf(photoItem1))
        }
        photoViewModel = PhotoViewModel(
            repositoryMock,
        )
    }

    @Test
    fun testInitialStateIsLoaded() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        val photosState = photoViewModel.photoListState
        Assert.assertTrue(photosState.first().isLoading)
        advanceUntilIdle()
        Assert.assertFalse(photosState.first().isLoading)
        Assert.assertEquals(photosState.first().items, listOf(photoItem1))
        coVerify {
            repositoryMock.getPhotoFlow()
        }
    }
}