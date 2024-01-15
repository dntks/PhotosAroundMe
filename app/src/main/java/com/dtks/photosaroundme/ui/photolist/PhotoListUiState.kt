package com.dtks.photosaroundme.ui.photolist

import com.dtks.photosaroundme.ui.photolist.model.PhotoItem


data class PhotoListUiState(
    val userMessage: Int? = null,
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val items: List<PhotoItem> = emptyList(),
)