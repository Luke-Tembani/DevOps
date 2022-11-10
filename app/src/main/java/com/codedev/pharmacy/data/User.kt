package com.codedev.pharmacy.data

data class User(
    val first_name:String,
    val last_name:String,
    val email:String,
    val imagePath:String =""

){
    constructor():this("","","","")
}
