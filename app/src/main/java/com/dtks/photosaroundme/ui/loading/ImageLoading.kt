package com.dtks.photosaroundme.ui.loading

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dtks.photosaroundme.R


@Preview
@Composable
fun PreviewImageLoading() {
    ImageLoading(modifier = Modifier.width(200.dp).height(200.dp))
}
@Composable
fun ImageLoading(modifier: Modifier) {
    val transition = rememberInfiniteTransition(
        label = stringResource(id = R.string.image_background_transition)
    )
    val borderColor by transition.animateColor(
        initialValue = MaterialTheme.colorScheme.primaryContainer,
        targetValue = MaterialTheme.colorScheme.inversePrimary,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = stringResource(id = R.string.image_background_label)
    )
    Box(
        modifier = modifier.background(color = borderColor)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .width(dimensionResource(id = R.dimen.progress_indicator_size)),
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}