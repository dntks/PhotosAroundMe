package com.dtks.photosaroundme.ui.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dtks.photosaroundme.R
import com.dtks.photosaroundme.ui.EmptyContent
import com.dtks.photosaroundme.ui.loading.LoadingContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListScreen(
    onStartStopClick: (Boolean) -> Unit,
    onPhotoItemClick: (PhotoItem) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PhotoViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.photosState.collectAsStateWithLifecycle()
    val error by viewModel.errorMessageFlow.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize(),
        topBar = {
            PhotoSearchTopBar(
                title = stringResource(id = R.string.app_name),
                onStartStopClick = onStartStopClick
            )
        },
        floatingActionButton = {}
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LoadingContent(
                empty = uiState.isEmpty,
                loading = uiState.isLoading,
                emptyContent = {
                    EmptyContent(R.string.no_phptos_found)
                },
                noInternetContent = {
                    EmptyContent(R.string.no_internet_connection)
                }
            ) {
                PhotoList(uiState, onPhotoItemClick)
            }

            uiState.userMessage?.let { userMessage ->
                showError(userMessage, viewModel, snackbarHostState)
            }
            error?.let { userMessage ->
                showError(userMessage, viewModel, snackbarHostState)
            }
        }
    }
}

@Composable
private fun showError(
    userMessage: Int,
    viewModel: PhotoViewModel,
    snackbarHostState: SnackbarHostState
) {
    val snackbarText = stringResource(userMessage)
    LaunchedEffect(viewModel, userMessage, snackbarText) {
        snackbarHostState.showSnackbar(snackbarText)
        viewModel.snackbarMessageShown()
    }
}
