package com.dtks.photosaroundme.ui.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.dtks.photosaroundme.R
import com.dtks.photosaroundme.utils.ConnectionState
import com.dtks.photosaroundme.utils.connectivityState

@Composable
fun LoadingContent(
    loading: Boolean,
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    noInternetContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val networkConnectivity by connectivityState()
    if (empty && loading) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(dimensionResource(id = R.dimen.progress_indicator_size)),
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    } else if (networkConnectivity == ConnectionState.Unavailable) {
        noInternetContent()
    }else if (empty) {
        emptyContent()
    } else {
        content()
    }

}