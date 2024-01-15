package com.dtks.photosaroundme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.photosaroundme.R
import com.dtks.photosaroundme.data.repository.PhotoRepository
import com.dtks.photosaroundme.ui.photolist.PhotoListUiState
import com.dtks.photosaroundme.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    photoRepository: PhotoRepository
) : ViewModel() {
    private val _errorMessageFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isStartedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isStartedFlow = _isStartedFlow.asStateFlow()
    val errorMessageFlow = _errorMessageFlow.asStateFlow()

    val photoListState = photoRepository.getPhotoFlow().map {
        PhotoListUiState(
            isLoading = false,
            isEmpty = it.isEmpty(),
            items = it
        )
    }.catch {
        PhotoListUiState(
            userMessage = R.string.error,
            isLoading = false,
            isEmpty = true
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = PhotoListUiState(isLoading = true)
        )

    fun setStarted(isStarted: Boolean) {
        _isStartedFlow.value = isStarted
    }

    fun setErrorMessage(errorMessage: Int) {
        _errorMessageFlow.value = errorMessage
    }

    fun snackbarMessageShown() {
        _errorMessageFlow.value = null
    }
}
