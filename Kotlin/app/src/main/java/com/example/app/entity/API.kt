package com.example.app.entity

import retrofit2.http.GET

interface API {
    @GET("reps/user")
    fun getRepos()
}