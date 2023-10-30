package com.arcondry.mypersonalpalette.core.network.networkConnectivity

import kotlinx.coroutines.flow.Flow

interface INetworkConnectivity {
    val networkStateFlow: Flow<Boolean>
    val wifiConnection:Boolean
    fun isConnected(): Boolean
}