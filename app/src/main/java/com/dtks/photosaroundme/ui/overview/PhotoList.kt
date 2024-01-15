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
import androidx.compose.ui.tooling.preview.Preview
import com.dtks.photosaroundme.ui.overview.model.PhotoItem
import com.dtks.photosaroundme.utils.Coordinates

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

@Preview
@Composable
fun PreviewEmptyPhotoList() {
    PhotoList(PhotoListUiState(
        isEmpty = true,
        isLoading = false
    )
    ){

    }
}
@Preview
@Composable
fun PreviewLoadingPhotoList() {
    PhotoList(PhotoListUiState(
        isEmpty = false,
        isLoading = true
    )
    ){

    }
}
@Preview
@Composable
fun PreviewTwoItemsPhotoList() {
    PhotoList(
        previewPhotoListUiStateWithTwoItems()
    ){

    }
}

@Composable
private fun previewPhotoListUiStateWithTwoItems() = PhotoListUiState(
    isEmpty = false,
    isLoading = false,
    items = listOf(
        PhotoItem(
            id = "Jim",
            title = "Regional manager",
            owner = "Ricky Gervais",
            server = "666",
            secret = "s4cr4t",
            coordinates = Coordinates(52.3676, 4.9041)
        ), PhotoItem(
            id = "Dwight",
            title = "Assistant to the Regional manager",
            owner = "Ricky Gervais",
            server = "666",
            secret = "s4cr4t",
            coordinates = Coordinates(42.3676, 4.9041)
        )
    )
)