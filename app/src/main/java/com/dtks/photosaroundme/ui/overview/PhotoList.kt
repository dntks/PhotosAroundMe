package com.dtks.photosaroundme.ui.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun PhotoList(
    uiState: PhotoListUiState,
    onPhotoItemClick: (PhotoItem) -> Unit,
) {
    val listState = rememberLazyListState()
    val items = uiState.items
    LaunchedEffect(items) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect {
                // Scroll to the top if a new item is added.
                // (But only if user is scrolled to the top already.)
                if (it <= 1) {
                    listState.scrollToItem(0)
                }
            }
    }
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