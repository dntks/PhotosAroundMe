package com.dtks.photosaroundme.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSearchTopBar(
    title: String,
    onStartStopClick: (Boolean) -> Unit
) {
    var isStarted by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            Text(text = if(isStarted)"Stop" else "Start", modifier = Modifier.clickable{
                isStarted = !isStarted
                onStartStopClick(isStarted)
            })
        },

        modifier = Modifier.fillMaxWidth()
    )
}