package com.example.generics

interface KotlinTest2<out T: Runnable> {
    fun get(): T
}