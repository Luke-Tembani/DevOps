package com.codedev.pharmacy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.codedev.pharmacy.R
import com.codedev.pharmacy.databinding.ActivityOrderMedicationBinding

class OrderMedicationActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityOrderMedicationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.orders)
        binding.bottomNav.setupWithNavController(navController)
    }
}