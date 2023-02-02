package com.cristian.castellanos.dogedex.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.composables.AuthField
import com.cristian.castellanos.dogedex.composables.BackNavigationIcon

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    onNavigationIconClick: () -> Unit,
    onSignupButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    authViewModel: AuthViewModel
) {
    Scaffold(
        topBar = { SignUpScreenToolbar(onNavigationIconClick) },
    ) {
        Content(resetFieldErrors = {authViewModel.resetErrors()} , onSignupButtonClick = onSignupButtonClick, authViewModel = authViewModel)
    }
}

@Composable
private fun Content(
    resetFieldErrors: () -> Unit,
    onSignupButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    authViewModel: AuthViewModel
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.email),
            email = email.value,
            onTextChanged = {
                email.value = it
                resetFieldErrors()
            },
            errorMessageId = authViewModel.emailError.value
        )
        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = stringResource(R.string.password),
            email = password.value,
            visualTransformation = PasswordVisualTransformation(),
            onTextChanged = {
                password.value = it
                resetFieldErrors()
            },
            errorMessageId = authViewModel.passwordError.value
        )
        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = stringResource(R.string.confirm_password),
            email = confirmPassword.value,
            visualTransformation = PasswordVisualTransformation(),
            onTextChanged = {
                confirmPassword.value = it
                resetFieldErrors()
            },
            errorMessageId = authViewModel.confirmPasswordError.value
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { onSignupButtonClick(email.value, password.value, confirmPassword.value) }) {
            Text(
                text = stringResource(R.string.sign_up),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SignUpScreenToolbar(
    onNavigationIconClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,
        navigationIcon = { BackNavigationIcon { onNavigationIconClick() } }
    )
}
