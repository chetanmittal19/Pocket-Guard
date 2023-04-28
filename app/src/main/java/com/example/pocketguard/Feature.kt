package com.example.pocketguard

data class Feature(val id: Int, val name: String, val iconId: Int)

data class Helpline(val name: String, val contact: String)

data class User(val id: Int? = null, val email: String? = null, val question: String? = null)

object DataHolder {
    var lt: Double = 0.0
    var lo: Double = 0.0
}