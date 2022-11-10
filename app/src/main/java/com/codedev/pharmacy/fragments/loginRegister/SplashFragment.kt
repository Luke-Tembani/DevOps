package com.codedev.pharmacy.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codedev.pharmacy.R
import com.codedev.pharmacy.activities.OrderMedicationActivity
import com.codedev.pharmacy.databinding.FragmentSplashBinding
import com.codedev.pharmacy.viewmodel.LoginViewModel
import com.codedev.pharmacy.viewmodel.SplashViewModel
import com.codedev.pharmacy.viewmodel.SplashViewModel.Companion.Account_Options_Fragment
import com.codedev.pharmacy.viewmodel.SplashViewModel.Companion.Order_Activity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashFragment:Fragment(R.layout.fragment_splash) {

    private lateinit var binding : FragmentSplashBinding
    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnExplore.setOnClickListener {
                findNavController().navigate(R.id.action_splashFragment_to_accountOptions)
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.navigate.collect{
                when(it){
                    Order_Activity ->{
                        Intent(requireActivity(),OrderMedicationActivity::class.java).also {
                                intent -> intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }

                    }

                    Account_Options_Fragment->{
                        viewModel.exploreBtnClick()
                        findNavController().navigate(R.id.action_splashFragment_to_accountOptions)
                    }

                    else -> Unit
                }
            }
        }
    }












}