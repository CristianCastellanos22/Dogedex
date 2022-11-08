package com.cristian.castellanos.dogedex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.databinding.ActivityDogDetailBinding
import com.cristian.castellanos.dogedex.model.Dog

class DogDetailActivity : AppCompatActivity() {

    private val viewModel: DogDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        with(binding) {
            dogIndex.text = getString(R.string.dog_index_format, dog.index)
            lifeExpectancy.text =
                getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
            binding.dog = dog
            dogImage.load(dog.imageUrl)

            //Se comenta por que se cambio el tipo de dato en el viewmodel, para poder funcionar con
            //compose
            /*viewModel.status.observe(this@DogDetailActivity) {
                when (it) {
                    is ApiResponseStatus.Error -> {
                        loadingWheel.visibility = View.GONE
                        Toast.makeText(this@DogDetailActivity, it.messageId, Toast.LENGTH_SHORT).show()
                    }
                    is ApiResponseStatus.Loading -> loadingWheel.visibility = View.VISIBLE
                    is ApiResponseStatus.Success -> {
                        loadingWheel.visibility = View.GONE
                        finish()
                    }
                }
            }*/

            closeButton.setOnClickListener {
                if (isRecognition) {
                    viewModel.addDogToUser(dog.id)
                } else {
                    finish()
                }
            }
        }
    }

    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

}