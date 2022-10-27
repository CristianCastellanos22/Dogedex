package com.cristian.castellanos.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cristian.castellanos.dogedex.databinding.FragmentLoginBinding
import com.cristian.castellanos.dogedex.isValidEmail

class LoginFragment : Fragment() {

    interface LoginFragmentActions {
        fun onRegisterButtonClick()
        fun onLoginFieldValidated(email: String, password: String)
    }

    private lateinit var loginFragmentActions: LoginFragmentActions
    private lateinit var binding: FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentsActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }
        binding.loginButton.setOnClickListener {
            validateFields()
        }
        return binding.root
    }

    private fun validateFields() {
        with(binding) {
            emailInput.error = ""
            passwordInput.error = ""

            val email = emailEdit.text.toString()
            if (!isValidEmail(email)) {
                emailInput.error =
                    getString(com.cristian.castellanos.dogedex.R.string.email_is_not_valid)
                return
            }

            val password = passwordEdit.text.toString()
            if (password.isEmpty()) {
                passwordInput.error =
                    getString(com.cristian.castellanos.dogedex.R.string.password_must_not_be_empty)
                return
            }

            loginFragmentActions.onLoginFieldValidated(email, password)
        }
    }
}