package com.dtks.photosaroundme.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.dtks.photosaroundme.R
import com.dtks.photosaroundme.data.Coordinates
import com.dtks.photosaroundme.ui.loading.ImageLoading
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun PhotoCard(
    modifier: Modifier,
    photoItem: PhotoItem,
    onClick: (PhotoItem) -> Unit,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(
            horizontal = dimensionResource(id = R.dimen.list_item_padding),
            vertical = dimensionResource(id = R.dimen.list_item_padding),
        )
//            .background(color = MaterialTheme.colorScheme.surface)
        .clickable { onClick(photoItem) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.list_item_height))
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.Center)
                    .height(
                        dimensionResource(id = R.dimen.list_item_height)
                    ),
                model = photoItem.createImageUrl(),
                loading = {
                    ImageLoading(
                        Modifier
                            .width(dimensionResource(id = R.dimen.list_item_image_width))
                            .height(dimensionResource(id = R.dimen.list_item_height)),
                    )
                },
                contentDescription = photoItem.title,
                contentScale = ContentScale.FillHeight
            )

            Column {
                Text(
                    text = photoItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            dimensionResource(id = R.dimen.horizontal_margin)
                        )
                )
                Text(
                    text = photoItem.coordinates.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            dimensionResource(id = R.dimen.horizontal_margin)
                        )
                )
                photoItem.createdTime?.let {

                    val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm")
                    val resultdate = Date(it)
                    Text(
                        text = sdf.format(resultdate),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            dimensionResource(id = R.dimen.horizontal_margin)
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewArtCard() {
    PhotoCard(modifier = Modifier, photoItem = PhotoItem(
        id = "Jim",
        title = "Regional manager",
        owner = "Ricky Gervais",
        server = "666",
        secret = "s4cr4t",
        coordinates = Coordinates(52.3676, 4.9041)
    ), onClick = {})
}