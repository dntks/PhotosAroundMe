package com.dtks.photosaroundme.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.dtks.photosaroundme.R

@Composable
fun EmptyContent(@StringRes emptyResultStringResource: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = emptyResultStringResource),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.horizontal_margin)
                )
                .fillMaxSize()
                .align(Alignment.Center)
        )
    }
}