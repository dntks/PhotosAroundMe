package com.dtks.photosaroundme.ui.photolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dtks.photosaroundme.viewmodel.PhotoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSearchTopBar(
    title: String,
    viewModel: PhotoViewModel = hiltViewModel(),
    onStartStopClick: (Boolean) -> Unit
) {
    val isStarted by viewModel.isStartedFlow.collectAsStateWithLifecycle()
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            Text(text = if(isStarted)"Stop" else "Start", modifier = Modifier.clickable{
                onStartStopClick(!isStarted)
            })
        },

        modifier = Modifier.fillMaxWidth().shadow(
            elevation = 5.dp, //or whatever value
            spotColor = Color.DarkGray,

        )
    )
}