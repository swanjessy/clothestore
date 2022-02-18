package com.example.clothestore.data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Resource<T> {

    return withContext(dispatcher) {
        try {

            Resource.Success(apiCall.invoke())

        } catch (throwable: Throwable) {
            Timber.e("$throwable   ${throwable.localizedMessage}")
            when (throwable) {
                is IOException -> {
                    Resource.Error(throwable.message.toString(), null)
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    Resource.Error(errorResponse?.message.toString(), null)
                }
                is KotlinNullPointerException -> {
                    Resource.Error("Null pointer Exception")
                }
                else -> {
                    Resource.Error("Some error has occurred", null)
                }
            }
        }
    }

}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {


    val s = throwable.response()?.errorBody()?.string()
    Timber.e(s)

    return if (s != null) {
        ErrorResponse(s)
    } else {
        null
    }

}
