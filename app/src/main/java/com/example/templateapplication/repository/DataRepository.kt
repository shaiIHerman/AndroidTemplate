package com.example.templateapplication.repository

import com.example.templateapplication.network.ApiOperation
import com.example.templateapplication.network.ApiService
import com.example.templateapplication.network.models.domain.Character
import com.example.templateapplication.network.models.remote.toDomainCharacter
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getDataList(): ApiOperation<List<Character>> {
        return try {
            ApiOperation.Success(data = apiService.getDataList().results.map { remoteCharacter -> remoteCharacter.toDomainCharacter() })
        } catch (e: Exception) {
            ApiOperation.Failure(e)
        }
    }

    suspend fun getDataObject(id: Int): ApiOperation<Character> {
        return try {
            ApiOperation.Success(data = apiService.getDataObject(id).toDomainCharacter())
        } catch (e: Exception) {
            ApiOperation.Failure(e)
        }
    }
}