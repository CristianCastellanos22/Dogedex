package com.cristian.castellanos.dogedex.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.auth.AuthNavDestination.LoginScreenDestination
import com.cristian.castellanos.dogedex.auth.AuthNavDestination.SignUpScreenDestination
import com.cristian.castellanos.dogedex.composables.ErrorDialog
import com.cristian.castellanos.dogedex.composables.LoadingWheel
import com.cristian.castellanos.dogedex.model.User

@Composable
fun AuthScreen(
    status: ApiResponseStatus<User>?,
    onLoginButtonClick: (String, String) -> Unit,
    onSignupButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    onErrorDialogDismiss: () -> Unit,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    AuthNavHost(
        navController = navController,
        onLoginButtonClick = onLoginButtonClick,
        onSignupButtonClick = onSignupButtonClick,
        authViewModel = authViewModel
    )
    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(status.messageId, onDialogDismiss = onErrorDialogDismiss)
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick: (String, String) -> Unit,
    onSignupButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    authViewModel: AuthViewModel
) {
    NavHost(navController = navController, startDestination = LoginScreenDestination) {
        composable(LoginScreenDestination) {
            LoginScreen(onRegisterButtonClick = {
                navController.navigate(
                    route = SignUpScreenDestination
                )
            }, onLoginButtonClick = onLoginButtonClick,
                authViewModel = authViewModel
            )
        }
        composable(SignUpScreenDestination) {
            SignUpScreen(
                onNavigationIconClick = { navController.navigateUp() },
                onSignupButtonClick = onSignupButtonClick,
                authViewModel = authViewModel
            )
        }
    }
}