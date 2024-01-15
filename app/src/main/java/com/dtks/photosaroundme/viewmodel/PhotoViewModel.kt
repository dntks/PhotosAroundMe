package com.dtks.photosaroundme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.photosaroundme.data.repository.PhotoRepository
import com.dtks.photosaroundme.ui.overview.PhotoListUiState
import com.dtks.photosaroundme.utils.Coordinates
import com.dtks.photosaroundme.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessageFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isStartedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
     val isStartedFlow = _isStartedFlow.asStateFlow()
     val errorMessageFlow = _errorMessageFlow.asStateFlow()

    val photosState = photoRepository.getPhotoFlow().map {
        PhotoListUiState(
            isLoading = false,
            isEmpty = it.isEmpty(),
            items = it
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = PhotoListUiState(isLoading = true)
        )

    val testCoordinates = Coordinates(52.3676, 4.9041)

    fun setStarted(isStarted: Boolean) {
        _isStartedFlow.value = isStarted
    }
    fun setErrorMessage(errorMessage: Int) {
        _errorMessageFlow.value = errorMessage
    }

    fun snackbarMessageShown() {

    }
}
