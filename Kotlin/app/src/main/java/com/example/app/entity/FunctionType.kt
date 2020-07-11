package com.example.app.entity

class View {
    fun setOnclickListener(listener: (View) -> Unit) {

    }

    val call: (View) -> Unit)

}

fun main() {
    val view = View()
    view.setOnclickListener(::onClick) //函数类型
}


fun onClick(view: View) {
    println("被点击了")
}