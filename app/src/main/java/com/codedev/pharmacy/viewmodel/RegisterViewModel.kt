package com.codedev.pharmacy.viewmodel

import androidx.lifecycle.ViewModel
import com.codedev.pharmacy.data.User
import com.codedev.pharmacy.util.*
import com.codedev.pharmacy.util.Constants.USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import java.security.PrivateKey
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseFirestore
): ViewModel() {
    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register:Flow<Resource<User>> = _register

    private val _validaiton = Channel<RegisterFieldsState>()
    val validation = _validaiton.receiveAsFlow()



    fun createUserAccountWithEmailAndPassword(user:User,password:String){

        if(checkValidation(user, password)) {
            runBlocking {
                _register.emit(Resource.Loading())
            }

            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    it.user?.let {
                        saveUserInfo(it.uid,user)
                    }

                }
                .addOnFailureListener {
                    _register.value = Resource.Error(it.message.toString())

                }

        }else{
            val registerFieldState = RegisterFieldsState(
                validateEmail(user.email),
                validatePassword(password)
            )

            runBlocking {
                _validaiton.send(registerFieldState)
            }
        }
    }

    private fun saveUserInfo(userUid:String,user:User) {
        database.collection(USERS_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }

            .addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
    }

    private fun checkValidation(user: User, password: String):Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)
        val shouldRegister =
            emailValidation is RegisterValidation.Success && passwordValidation is RegisterValidation.Success

        return shouldRegister
    }

}