package com.example.app.entity

import retrofit2.Retrofit

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

/**
 * inline + reified 达到真泛型的效果
 */
val RETROFIT = Retrofit.Builder()
        .baseUrl("https://api.hencoder.com/")
        .build()

inline fun <reified T> create(): T{
    return RETROFIT.create(T::class.java)
}

val api = create<API>()


fun execute(): User{
    return User("a","b","s")
}


//类委托
interface Base{
    fun print()
}

class BaseImpl(val x: Int): Base{
    override fun print() {
        print(x)
    }
}


//Derived 的print实现会通过构造参数中的 b对象 来实现
class Derived(b: Base): Base by b