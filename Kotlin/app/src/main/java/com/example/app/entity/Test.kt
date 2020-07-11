package com.example.app.entity

import com.example.core.BaseApplication

fun main() {
    val user = User("baby","ss","23")

    val userCopy = user.copy()

    println(user)
    println(userCopy)
    println(user == userCopy)
    println(user === userCopy)

    val (name, password, code) = execute() //解构
    println("$name + $password + $code")

    repeat(100){
        println(it)
    }

    val array = arrayOf(1, 23, 4, 54, 43, 45, 9, 34)
    for (index in 0.until(array.size)){

    }

    for (index in array.indices){

    }

}

fun execute(): User{
    return User("a","b","s")
}
