package com.dtks.photosaroundme.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn

fun Context.isInternetAvailable(): Boolean {
    var result = false
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }

    return result
}
@ExperimentalCoroutinesApi
private fun Context.observeConnectivityAsFlow() =
    callbackFlow {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        fun sendConnectionState() =
                    if (isInternetAvailable()) {
                        trySend(ConnectionState.Available)
                    } else {
                        trySend(ConnectionState.Unavailable)
                    }

        val networkRequest =
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

        val networkCallback =
            object : ConnectivityManager.NetworkCallback() {
                // satisfies network capability and transport requirements requested in networkRequest
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    sendConnectionState()
                }
                override fun onLost(network: Network) {
                    sendConnectionState()
                }
            }

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
private data class NetworkState(
    /** Determines if the network is connected. */
    val isConnected: Boolean,

    /** Determines if the network is validated - has a working Internet connection. */
    val isValidated: Boolean,

    /** Determines if the network is metered. */
    val isMetered: Boolean,

    /** Determines if the network is not roaming. */
    val isNotRoaming: Boolean
)

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    return produceState<ConnectionState>(initialValue = ConnectionState.Unavailable) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}