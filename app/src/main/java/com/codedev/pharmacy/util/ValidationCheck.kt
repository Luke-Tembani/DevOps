package com.codedev.pharmacy.util

import android.util.Patterns

fun validateEmail(email:String) : RegisterValidation{
    if(email.isEmpty())
        return RegisterValidation.Failed("Email Cannot Be Blank!")

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong Email Format!")

    else
        return RegisterValidation.Success
}

fun validatePassword(password:String) :RegisterValidation{
    if(password.isEmpty())
        return RegisterValidation.Failed("Password Cannot Be Blank!")

    if(password.length < 6)
        return RegisterValidation.Failed("Password Should Contain at least 6 characters")

    else
        return RegisterValidation.Success
}