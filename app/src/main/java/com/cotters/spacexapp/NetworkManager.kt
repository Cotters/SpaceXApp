package com.cotters.spacexapp

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class NetworkManager(private val connectivityManager: ConnectivityManager?) {

    private var _hasNetwork = mutableStateOf(false)
    var hasNetwork: State<Boolean> = _hasNetwork

    fun listenToNetworkEvents() {
        connectivityManager?.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d("JCtest", "onAvailable")
                _hasNetwork.value = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d("JCtest", "onLost")
                _hasNetwork.value = false
            }

            override fun onUnavailable() {
                super.onUnavailable()
                Log.d("JCtest", "onUnavailable")
                _hasNetwork.value = false
            }

            // Network capabilities have changed for the network
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities,
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                Log.d("JCtest", "onChanged")
                val hasCellular =
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                val hasWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                _hasNetwork.value = hasCellular || hasWifi
            }
        })
    }

}
