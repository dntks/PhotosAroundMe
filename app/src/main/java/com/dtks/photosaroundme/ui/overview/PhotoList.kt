package com.dtks.photosaroundme.ui.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun PhotoList(
    uiState: OverviewUiState,
    onPhotoItemClick: (PhotoItem) -> Unit,
) {
    val listState = rememberLazyListState()
    val items = uiState.items
    LazyColumn(
        state = listState,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        items(items, key = { it.id }) { photo ->
            PhotoCard(
                modifier = Modifier.animateItemPlacement(),
                photoItem = photo,
                onClick = onPhotoItemClick,
            )
        }
    }
}