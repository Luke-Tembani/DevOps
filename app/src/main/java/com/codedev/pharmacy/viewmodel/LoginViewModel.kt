package com.codedev.pharmacy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codedev.pharmacy.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private var FirebaseAuth:FirebaseAuth

) :ViewModel() {

    private var _login = MutableSharedFlow<Resource<FirebaseUser>>()
    var login = _login.asSharedFlow()

    private var _resetPassword = MutableSharedFlow<Resource<String>>()
    var resetPassword = _resetPassword.asSharedFlow()

    fun loginWithEmailAndPassword(email:String,password:String){
        viewModelScope.launch { _login.emit(Resource.Loading()) }
        FirebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                viewModelScope.launch {
                    it.user?.let {
                        _login.emit(Resource.Success(it))
                    }
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _login.emit(Resource.Error(it.message.toString()))
                }

            }


    }

    fun resetPassword(email:String){
        viewModelScope.launch {
            _resetPassword.emit(Resource.Loading())
        }

        FirebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {

                viewModelScope.launch {
                    _resetPassword.emit(Resource.Success(email))
                }

            }

            .addOnFailureListener {
                viewModelScope.launch {
                    _resetPassword.emit(Resource.Error(it.message.toString()))
                }

            }



    }

}