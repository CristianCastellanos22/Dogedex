package com.cristian.castellanos.dogedex.machinelearning

import androidx.camera.core.ImageProxy

interface ClassifierTasks {
    suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition
}