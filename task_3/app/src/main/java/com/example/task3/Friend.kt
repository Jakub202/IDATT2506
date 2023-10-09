package com.example.task3
import java.io.Serializable

data class Friend(
    val name: String,
    val birthDate: String
) : Serializable{
    override fun toString(): String {
        return "$name - $birthDate"
    }
}
