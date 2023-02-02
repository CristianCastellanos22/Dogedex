package com.cristian.castellanos.dogedex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import com.cristian.castellanos.dogedex.dogdetail.ui.theme.DogedexTheme
import com.cristian.castellanos.dogedex.main.MainActivity
import com.cristian.castellanos.dogedex.model.User
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user = viewModel.user
            val userValue = user.value
            if (userValue != null) {
                User.setLoggedInUser(this, userValue)
                starMainActivity()
            }
            val status = viewModel.status
            DogedexTheme {
                AuthScreen(
                    status = status.value,
                    onLoginButtonClick = { email, password ->
                        viewModel.login(email, password)
                    },
                    onSignupButtonClick = { email, password, passwordConfirmation ->
                        viewModel.signUp(email, password, passwordConfirmation)
                    },
                    onErrorDialogDismiss = ::resetApiResponseStatus,
                    authViewModel = viewModel
                )
            }
        }

    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun starMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}