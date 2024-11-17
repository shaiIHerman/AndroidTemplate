package com.example.templateapplication.repository

import com.example.templateapplication.network.ApiService
import com.example.templateapplication.network.models.domain.Character
import com.example.templateapplication.network.models.remote.toDomainCharacter
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getDataList(): List<Character> {
        return apiService.getDataList().results.map { remoteCharacter -> remoteCharacter.toDomainCharacter() }
    }

}