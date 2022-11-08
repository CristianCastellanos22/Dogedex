package com.cristian.castellanos.dogedex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import coil.annotation.ExperimentalCoilApi
import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.dogdetail.ui.theme.DogedexTheme
import com.cristian.castellanos.dogedex.model.Dog

@ExperimentalCoilApi
class DogDetailComposeActivity : ComponentActivity() {

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            val status = viewModel.status
            if (status.value is ApiResponseStatus.Success) {
                finish()
            } else {
                DogedexTheme {
                    DogDetailScreen(
                        dog = dog,
                        status = status.value,
                        onButtonClicked = { onButtonClicked(dog.id, isRecognition) },
                        onErrorDialogDismiss = {
                            resetApiResponseStatus()
                        }
                    )
                }
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun onButtonClicked(dogId: Long, isRecognition: Boolean) {
        if (isRecognition) {
            viewModel.addDogToUser(dogId)
        } else {
            finish()
        }
    }

    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

}
