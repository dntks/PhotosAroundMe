package com.dtks.photosaroundme.ui.overview


data class PhotoListUiState(
    val userMessage: Int? = null,
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val items: List<PhotoItem> = emptyList(),
)