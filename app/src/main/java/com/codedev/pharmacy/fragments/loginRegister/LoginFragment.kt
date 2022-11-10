package com.codedev.pharmacy.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codedev.pharmacy.R
import com.codedev.pharmacy.activities.OrderMedicationActivity
import com.codedev.pharmacy.databinding.FragmentLoginBinding
import com.codedev.pharmacy.dialogs.setBottomDialog
import com.codedev.pharmacy.util.Resource
import com.codedev.pharmacy.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment:Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSignIn.setOnClickListener {
                var email = email.text.toString().trim()
                var password = password.text.toString()
                viewModel.loginWithEmailAndPassword(email,password)

            }


        }

        binding.apply {
            signUpLink.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        //Forgot Password
        binding.forgotPassword.setOnClickListener {
            setBottomDialog { email ->
                viewModel.resetPassword(email)

            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect{

                when(it){

                    is Resource.Loading->{

                    }

                    is Resource.Success ->{

                        Snackbar.make(requireView(),"A reset link has been sent to your email",Snackbar.LENGTH_LONG).show()

                    }

                    is Resource.Error ->{
                        Snackbar.make(requireView(),"${it.message}",Snackbar.LENGTH_LONG).show()

                    }

                    else -> Unit
                }

            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect{
                when(it){

                    is Resource.Loading->{
                        binding.btnSignIn.startAnimation()
                    }

                    is Resource.Success ->{
                        binding.btnSignIn.revertAnimation()
                        Intent(requireActivity(),OrderMedicationActivity::class.java).also {
                             intent -> intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }

                    }

                    is Resource.Error ->{
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                        binding.btnSignIn.revertAnimation()
                    }

                    else -> Unit
                }

            }
        }


    }
}