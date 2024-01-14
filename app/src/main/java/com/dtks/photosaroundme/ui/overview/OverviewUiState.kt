package com.dtks.photosaroundme.ui.overview


data class OverviewUiState(
    val userMessage: Int? = null,
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val items: List<PhotoItem> = emptyList(),
)