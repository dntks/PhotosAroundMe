package com.dtks.photosaroundme.ui.overview

import com.dtks.photosaroundme.ui.overview.model.PhotoItem


data class PhotoListUiState(
    val userMessage: Int? = null,
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val items: List<PhotoItem> = emptyList(),
)