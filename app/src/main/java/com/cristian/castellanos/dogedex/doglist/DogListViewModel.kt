package com.cristian.castellanos.dogedex.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristian.castellanos.dogedex.model.Dog
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>> get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>> get() = _status

    private val dogRepository = DogRepository()

    init {
        getDogCollection()
    }

    private fun getDogCollection() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        val list = emptyList<Dog>()
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dogList.value = apiResponseStatus.data ?: list
        }
        _status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

}