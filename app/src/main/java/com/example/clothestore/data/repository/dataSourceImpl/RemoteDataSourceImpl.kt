package com.example.clothestore.data.repository.dataSourceImpl

import com.example.clothestore.data.api.APIService
import com.example.clothestore.data.model.APIResponse
import com.example.clothestore.data.repository.dataSource.RemoteDataSource
import com.example.clothestore.data.util.Resource
import com.example.clothestore.data.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: APIService
) : RemoteDataSource {
    override suspend fun getCatalogue(): Flow<Resource<APIResponse>> {
        return flow {
            val response = safeApiCall(Dispatchers.IO) {
                apiService.getCatalogue()
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}