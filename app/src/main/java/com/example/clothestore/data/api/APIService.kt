package com.example.clothestore.data.api

import com.example.clothestore.data.model.APIResponse
import retrofit2.http.GET


interface APIService {

    @GET("/0f78766a6d68832d309d")
    suspend fun getCatalogue(): APIResponse
}