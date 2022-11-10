package com.codedev.pharmacy.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codedev.pharmacy.R
import com.codedev.pharmacy.databinding.FragmentAccountOptionsBinding

class AccountOptions:Fragment(R.layout.fragment_account_options) {
    private lateinit var binding:FragmentAccountOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_accountOptions_to_loginFragment2)
            }
        }

        binding.apply {
            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_accountOptions_to_registerFragment)
            }
        }


    }
}