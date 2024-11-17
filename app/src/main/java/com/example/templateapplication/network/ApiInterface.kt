package com.example.templateapplication.network

import com.example.templateapplication.network.models.remote.AllCharacters
import com.example.templateapplication.network.models.remote.RemoteCharacter
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    suspend fun getDataList(): AllCharacters

    @GET("character/{id}")
    suspend fun getDataObject(@Path("id") id: Int): RemoteCharacter
}

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Failure<T>(val exception: Exception) : ApiOperation<T>

    suspend fun onSuccess(block: suspend (T) -> Unit): ApiOperation<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) block(exception)
        return this
    }
}