package com.cristian.castellanos.dogedex.di

import com.cristian.castellanos.dogedex.machinelearning.ClassifierRepository
import com.cristian.castellanos.dogedex.machinelearning.ClassifierTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassifierModule {

    @Binds
    abstract fun bindClassifierTasks(classifierRepository: ClassifierRepository): ClassifierTasks
}