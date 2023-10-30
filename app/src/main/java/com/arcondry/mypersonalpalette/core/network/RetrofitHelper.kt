package com.arcondry.mypersonalpalette.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> doRequest(call: suspend () -> Response<@JvmSuppressWildcards T>): ResponseWrapper<@JvmSuppressWildcards T> =
    withContext(Dispatchers.IO) {
        try {
            val response = call()

            if (response.isSuccessful)
                ResponseWrapper.Success<@JvmSuppressWildcards T>(response.body()!!)
            else
                ResponseWrapper.Error("")
        } catch (e: Exception) {
            e
            ResponseWrapper.Error("")
        }
    }