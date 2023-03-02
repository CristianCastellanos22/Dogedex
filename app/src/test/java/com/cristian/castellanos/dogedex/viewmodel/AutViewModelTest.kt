package com.cristian.castellanos.dogedex.viewmodel

import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.auth.AuthTasks
import com.cristian.castellanos.dogedex.auth.AuthViewModel
import com.cristian.castellanos.dogedex.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class AutViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = DogedexCoroutineRule()

    val fakeUser = User(
        id = 1,
        email = "test@gmail.com",
        authenticationToken = ""
    )

    @Test
    fun testLoginValidationCorrect() {
        class FakeAuthRepository : AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1, "test@gmail.com", ""))
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1, "", ""))
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        viewModel.login("test@gmail.com", "")
        assertEquals(R.string.password_must_not_be_empty, viewModel.passwordError.value)

        // Test empty email
        viewModel.login("", "password")
        assertEquals(R.string.email_is_not_valid, viewModel.emailError.value)

    }

    @Test
    fun testLoginStatesCorrect() {

        class FakeAuthRepository : AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1, "", ""))
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        viewModel.login("test@gmail.com", "test1234")
        assertEquals(fakeUser.email, viewModel.user.value?.email)
    }

    @Test
    fun `signUp should return an error if email is empty`() {

        class FakeAuthRepository : AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1, "", ""))
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        val email = ""
        val password = "password"
        val passwordConfirmation = "password"

        viewModel.signUp(email, password, passwordConfirmation)

        assertEquals(R.string.email_is_not_valid, viewModel.emailError.value)
    }

    @Test
    fun `signUp should return an error if password is empty`() {

        class FakeAuthRepository : AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1, "", ""))
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        val email = "test@example.com"
        val password = ""
        val passwordConfirmation = "password"

        viewModel.signUp(email, password, passwordConfirmation)

        assertEquals(R.string.password_must_not_be_empty, viewModel.passwordError.value)
    }

    @Test
    fun `signUp should return an error if passwords do not match`() {

        class FakeAuthRepository : AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String,
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1, "", ""))
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        val email = "test@example.com"
        val password = "password1"
        val passwordConfirmation = "password2"

        viewModel.signUp(email, password, passwordConfirmation)

        assertEquals(R.string.passwords_do_not_match, viewModel.confirmPasswordError.value)
        assertEquals(R.string.passwords_do_not_match, viewModel.confirmPasswordError.value)
    }

}