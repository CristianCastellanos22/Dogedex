package com.cristian.castellanos.dogedex.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.cristian.castellanos.dogedex.MainActivity
import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.databinding.ActivityLoginBinding
import com.cristian.castellanos.dogedex.model.User

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions,
    SignUpFragment.SignUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this) {
            when (it) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheelLogin.visibility = View.GONE
                    showErrorDialog(it.messageId)
                }
                is ApiResponseStatus.Loading -> binding.loadingWheelLogin.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.loadingWheelLogin.visibility = View.GONE
            }
        }
        viewModel.user.observe(this) {
            if (it != null) {
                User.setLoggedInUser(this, it)
                starMainActivity()
            }
        }
    }

    private fun starMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment).navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment4())
    }

    override fun onLoginFieldValidated(email: String, password: String) {
        viewModel.login(email, password)
    }

    override fun onSignUpFieldsValidate(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModel.signUp(email, password, passwordConfirmation)
    }

    private fun showErrorDialog(messageId: Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.there_was_an_error)
            .setMessage(messageId)
            .setPositiveButton(android.R.string.ok) {_,_ ->}
            .create().show()
    }

}