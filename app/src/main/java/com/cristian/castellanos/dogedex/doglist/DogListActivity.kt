package com.cristian.castellanos.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.recyclerview.widget.GridLayoutManager
import coil.annotation.ExperimentalCoilApi
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.databinding.ActivityDogListBinding
import com.cristian.castellanos.dogedex.dogdetail.DogDetailActivity
import com.cristian.castellanos.dogedex.dogdetail.DogDetailComposeActivity
import com.cristian.castellanos.dogedex.dogdetail.ui.theme.DogedexTheme
import com.cristian.castellanos.dogedex.model.Dog

@ExperimentalMaterialApi
@ExperimentalCoilApi
class DogListActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {
            DogedexTheme {
                val dogList = viewModel.dogList
                DogListScreen(
                    dogList = dogList.value,
                    onDogClicked = ::openDogDetailActivity
                )
            }
        }
    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailActivity::class.java)
        intent.putExtra(DogDetailComposeActivity.DOG_KEY, dog)
        startActivity(intent)
    }

}