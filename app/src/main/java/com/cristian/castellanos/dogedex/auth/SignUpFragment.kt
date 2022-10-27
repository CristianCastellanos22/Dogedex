package com.cristian.castellanos.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.databinding.FragmentSignUpBinding
import com.cristian.castellanos.dogedex.isValidEmail

class SignUpFragment : Fragment() {

    interface  SignUpFragmentActions {
        fun onSignUpFieldsValidate(email: String, password: String, passwordConfirmation: String)
    }

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement FragmentSignUpBinding")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        setupSingUpButton()
        return binding.root
    }

    private fun setupSingUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        with(binding) {
            emailInput.error = ""
            passwordInput.error = ""
            confirmPasswordInput.error = ""

            val email = emailEdit.text.toString()
            if (!isValidEmail(email)) {
                emailInput.error = getString(R.string.email_is_not_valid)
                return
            }

            val password = passwordEdit.text.toString()
            if (password.isEmpty()) {
                passwordInput.error = getString(R.string.password_must_not_be_empty)
                return
            }

            val passwordConfirmation = binding.confirmPasswordEdit.text.toString()
            if (passwordConfirmation.isEmpty()) {
                confirmPasswordInput.error = getString(R.string.password_must_not_be_empty)
                return
            }

            if (password != passwordConfirmation) {
                passwordInput.error = getString(R.string.passwords_do_not_match)
                return
            }

            signUpFragmentActions.onSignUpFieldsValidate(email, password, passwordConfirmation)
        }
    }

}