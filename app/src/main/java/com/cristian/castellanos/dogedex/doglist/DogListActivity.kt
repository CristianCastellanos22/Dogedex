package com.cristian.castellanos.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import coil.annotation.ExperimentalCoilApi
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.databinding.ActivityDogListBinding
import com.cristian.castellanos.dogedex.dogdetail.DogDetailComposeActivity

private const val GRID_SPAN_COUNT = 3

@ExperimentalCoilApi
class DogListActivity : AppCompatActivity() {

    private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWheel = binding.loadingWheel

        val recycler = binding.dogRecycler
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        val adapter = DogAdapter()
        adapter.setOnItemClickListener {
            val intent = Intent(this, DogDetailComposeActivity::class.java)
            intent.putExtra(DogDetailComposeActivity.DOG_KEY, it)
            startActivity(intent)
        }
        recycler.adapter = adapter

        viewModel.dogList.observe(this) {
            adapter.submitList(it)
        }
        binding.dogRecycler.adapter = adapter

        viewModel.status.observe(this) {
            when (it) {
                is ApiResponseStatus.Error -> {
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, it.messageId, Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> loadingWheel.visibility = View.GONE
            }
        }
    }

}