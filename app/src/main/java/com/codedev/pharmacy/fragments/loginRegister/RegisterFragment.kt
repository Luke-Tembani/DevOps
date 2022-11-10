package com.codedev.pharmacy.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codedev.pharmacy.R
import com.codedev.pharmacy.data.User
import com.codedev.pharmacy.databinding.FragmentRegisterBinding
import com.codedev.pharmacy.util.RegisterValidation
import com.codedev.pharmacy.util.Resource
import com.codedev.pharmacy.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

private val TAG = "Register Fragment"
@AndroidEntryPoint
class RegisterFragment:Fragment(R.layout.fragment_register) {

    private val viewModel by viewModels<RegisterViewModel>()

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSignUp.setOnClickListener {
                val user = User(
                 firstName.text.toString().trim(),
                 lastName.text.toString().trim(),
                 email.text.toString().trim()
                )
                val password = password.text.toString()

                Log.d(tag,"EMAIL LOGGED"+email.text.toString().trim())

                viewModel.createUserAccountWithEmailAndPassword(user, password)

            }
        }

        binding.signInLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }



        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.btnSignUp.startAnimation()
                    }
                    is Resource.Success -> {
                        Log.d("TEST",it.data.toString())
                        binding.btnSignUp.revertAnimation()
                    }
                    is Resource.Error -> {
                        Log.e(TAG,it.message.toString())
                        binding.btnSignUp.revertAnimation()

                    }

                    else -> Unit
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect{
                validation ->
                if(validation.email is RegisterValidation.Failed){

                    withContext(Dispatchers.Main){
                        binding.email.apply {
                            requestFocus()
                            error = validation.email.message

                        }
                    }

                }

                if(validation.password is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.password.apply {
                            requestFocus()
                            error = validation.password.message

                        }
                    }

                }
            }

        }
    }
}