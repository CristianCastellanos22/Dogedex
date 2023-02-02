package com.cristian.castellanos.dogedex.di

import com.cristian.castellanos.dogedex.doglist.DogRepository
import com.cristian.castellanos.dogedex.doglist.DogTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DogTasksModule {
    @Binds
    abstract fun bindDogTasks(
        dogRepository: DogRepository
    ): DogTasks
}