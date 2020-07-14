package com.example.app.entity

import okhttp3.Request
import okhttp3.Response

class View {
    fun setOnclickListener(listener: (View) -> Unit) {

    }

    fun setOnTouchListener(listener: (Int, Int) -> Unit) {

    }

    lateinit var call: (Request) -> Response?

}

fun main() {
    val view = View()
    view.setOnclickListener(::onClick) //函数类型
}


fun onClick(view: View) {
    println("被点击了")
}