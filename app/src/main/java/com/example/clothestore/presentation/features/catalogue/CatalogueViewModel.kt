package com.example.clothestore.presentation.features.catalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothestore.data.model.APIResponse
import com.example.clothestore.data.util.Resource
import com.example.clothestore.domain.usecase.GetCatalogueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatalogueViewModel @Inject constructor(
    private val useCase: GetCatalogueUseCase
) : ViewModel() {


    private val _productsFlow = Channel<Resource<APIResponse>>(Channel.BUFFERED)
    val productFlow = _productsFlow.receiveAsFlow()

    fun getCatalogueResponseFlow() = viewModelScope.launch {
        useCase.getCatalogue()
            .onStart {
                _productsFlow.send(Resource.Loading())

            }.catch { exception ->
                _productsFlow.send(Resource.Error(exception.message.toString()))

            }.collect { response ->
                _productsFlow.send(response)
            }
    }


}
