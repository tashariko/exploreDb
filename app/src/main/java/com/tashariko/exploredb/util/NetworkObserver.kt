package com.tashariko.exploredb.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.flow

object NetworkObserver: ConnectivityManager.NetworkCallback() {

    private val netLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getNetLiveData(context: Context): LiveData<Boolean> {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), this)
        }

        var isNetAvailable = false

        connectivityManager.allNetworks.forEach { network -> val netCapabilities = connectivityManager.getNetworkCapabilities(network)

            netCapabilities?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isNetAvailable = true
                    return@forEach
                }
            }
        }

        netLiveData.postValue(isNetAvailable)

        return netLiveData
    }

    override fun onAvailable(network: Network?) {
        netLiveData.postValue(true)
    }

    override fun onLost(network: Network?) {
        netLiveData.postValue(false)
    }
}
//
//
//class NetworkObserver {
//    fun network(context: Context) = flow<Boolean> {
//            val connectivityManager =
//                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                connectivityManager.registerDefaultNetworkCallback(object :
//                    ConnectivityManager.NetworkCallback() {
//                    override fun onLost(network: Network?) {
//
//                    }
//
//                    override fun onAvailable(network: Network?) {
//
//                    }
//                })
//            } else {
//                val builder = NetworkRequest.Builder()
//                connectivityManager.registerNetworkCallback(builder.build(),
//                    object : ConnectivityManager.NetworkCallback() {
//                        override fun onLost(network: Network?) {
//
//                        }
//
//                        override fun onAvailable(network: Network?) {
//
//                        }
//                    })
//            }
//
//            var isNetAvailable = false
//
//            connectivityManager.allNetworks.forEach { network ->
//                val netCapabilities = connectivityManager.getNetworkCapabilities(network)
//
//                netCapabilities?.let {
//                    if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
//                        isNetAvailable = true
//                        return@forEach
//                    }
//                }
//            }
//
//        }
//}