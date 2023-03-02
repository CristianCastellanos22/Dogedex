package com.cristian.castellanos.dogedex.viewmodel

import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.doglist.DogListViewModel
import com.cristian.castellanos.dogedex.doglist.DogTasks
import com.cristian.castellanos.dogedex.model.Dog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

class DogListViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = DogedexCoroutineRule()

    @Test
    fun downloadDogListStatusesCorrect() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        Dog(
                            1, 1, "", "", "", "", "",
                            "", "", "", "", inCollection = false
                        ),
                        Dog(
                            19, 2, "", "", "", "", "",
                            "", "", "", "", inCollection = false
                        )
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(
                    Dog(
                        1, 1, "", "", "", "", "",
                        "", "", "", "", inCollection = false
                    )
                )
            }
        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assertEquals(2, viewModel.dogList.value.size)
        assertEquals(19, viewModel.dogList.value[1].id)
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListErrorStatusesCorrect() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(12)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(
                    Dog(
                        1, 1, "", "", "", "", "",
                        "", "", "", "", inCollection = false
                    )
                )
            }
        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assertEquals(0, viewModel.dogList.value.size,)
        assert(viewModel.status.value is ApiResponseStatus.Error)
    }

    @Test
    fun resetStatusCorrect() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(12)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(
                    Dog(
                        1, 1, "", "", "", "", "",
                        "", "", "", "", inCollection = false
                    )
                )
            }
        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assert(viewModel.status.value is ApiResponseStatus.Error)
        viewModel.resetApiResponseStatus()
        assert(viewModel.status.value == null)
    }

}