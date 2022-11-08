package com.cristian.castellanos.dogedex.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.doglist.DogRepository
import kotlinx.coroutines.launch

class DogDetailViewModel: ViewModel() {

    //MutableLiveData se usa para binding y programacion en xml
    /*private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>> get() = _status*/

    //
    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
    private set

    private val dogRepository = DogRepository()

    fun addDogToUser(dogId: Long) {
        status.value = ApiResponseStatus.Error(messageId = R.string.password_must_not_be_empty)
        /*viewModelScope.launch {
            //para uso con MutableLiveData
            //_status.value = ApiResponseStatus.Loading()
            status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }*/
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        //para uso con MutableLiveData
        //_status.value = apiResponseStatus
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }
}