package com.dtks.photosaroundme.ui.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.photosaroundme.data.Coordinates
import com.dtks.photosaroundme.data.model.SearchRequest
import com.dtks.photosaroundme.data.repository.PhotoRepository
import com.dtks.photosaroundme.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

    private val _updateState: MutableStateFlow<OverviewUiState> = MutableStateFlow(
        OverviewUiState(isEmpty = true, isLoading = false)
    )
    val updateState = photoRepository.getPhotoFlow().map {
        OverviewUiState(
            isLoading = false,
            isEmpty = it.isEmpty(),
            items = it
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = OverviewUiState(isLoading = true)
        )

    val testCoordinates = Coordinates(52.3676, 4.9041)

    fun snackbarMessageShown() {
        _userMessage.value = null
    }

    fun loadPhotos() {
        _isLoading.value = true

        viewModelScope.launch {
            photoRepository.getPhotoFlow(
            ).catch {
                Log.e("BAJ ", it.cause.toString())
            }
                .onEach { newState ->
                    _updateState.value = OverviewUiState(
                        isLoading = false,
                        isEmpty = newState.isEmpty(),
                        items = newState
                    )

                }.collect()
        }
    }

    fun loadPhotoAt(coordinates: Coordinates = testCoordinates) {
        _isLoading.value = true

        viewModelScope.launch {
            photoRepository.firstPhotoResponseFlow(
                SearchRequest(coordinates)
            ).catch {
                Log.e("ERROR", it.cause?.toString()?:"")
            }
                .onEach { photoItem ->
                    photoItem?.let{
                        photoRepository.savePhoto(it)
                    }
                }.collect()
        }
    }
}
