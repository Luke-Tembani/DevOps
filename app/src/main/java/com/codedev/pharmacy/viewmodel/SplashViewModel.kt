package com.codedev.pharmacy.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codedev.pharmacy.R
import com.codedev.pharmacy.util.Constants.SPLASH_KEY
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
):ViewModel() {

    private val _navigate = MutableStateFlow(0)
    val navigate:StateFlow<Int> = _navigate

    companion object{
        const val Order_Activity = 12
        const val Account_Options_Fragment = R.id.action_splashFragment_to_accountOptions
    }

    init {
        val _isBtnClicked = sharedPreferences.getBoolean(SPLASH_KEY,false)
        val user = firebaseAuth.currentUser

        if(user != null){

            viewModelScope.launch {
                _navigate.emit(Order_Activity)
            }

        }else if(_isBtnClicked){
            viewModelScope.launch {
                _navigate.emit(Account_Options_Fragment)
            }

        }
        else{
            Unit

        }
    }

    fun exploreBtnClick(){
        sharedPreferences.edit().putBoolean(SPLASH_KEY,true).apply()
    }
}