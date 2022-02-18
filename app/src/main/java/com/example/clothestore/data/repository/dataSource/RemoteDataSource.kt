package com.example.clothestore.data.repository.dataSource

import com.example.clothestore.data.model.APIResponse
import com.example.clothestore.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getCatalogue(): Flow<Resource<APIResponse>>
}