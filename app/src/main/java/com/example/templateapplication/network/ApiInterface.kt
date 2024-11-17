package com.example.templateapplication.network

import com.example.templateapplication.network.models.remote.AllCharacters
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    suspend fun getDataList(): AllCharacters
}