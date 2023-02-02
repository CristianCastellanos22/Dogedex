package com.cristian.castellanos.dogedex.di

import com.cristian.castellanos.dogedex.auth.AuthRepository
import com.cristian.castellanos.dogedex.auth.AuthTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthTaskModule {

    @Binds
    abstract fun bindDogTasks(authRepository: AuthRepository): AuthTasks

}