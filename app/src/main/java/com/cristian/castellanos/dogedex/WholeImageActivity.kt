package com.cristian.castellanos.dogedex

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.cristian.castellanos.dogedex.databinding.ActivityWholeImageBinding
import java.io.File

class WholeImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWholeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_URI_KEY) ?: ""
        val uri = Uri.parse(photoUri)

        if (uri.path == null) {
            Toast.makeText(this, "Error showing image, no photo uri", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.wholeImage.load(File(uri.path))
    }

    companion object {
        const val PHOTO_URI_KEY = "photo_uri"
    }
}