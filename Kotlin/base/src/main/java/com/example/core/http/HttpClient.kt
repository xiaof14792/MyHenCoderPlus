package com.example.core.http

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

object HttpClient : OkHttpClient() {
    private val gson = Gson()

    private fun <T> convert(json: String?, type: Type): T {
        return gson.fromJson(json, type);
    }

    fun <T> get(path: String, type: Type, entityCallback: EntityCallback<T>) {
        val request = Request.Builder()
                .url("https://api.hencoder.com/$path")
                .build();
        val call = this.newCall(request)

        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val code = response.code();
                when (code) {
                    in 200..299 -> entityCallback.onSuccess(convert(response.body()?.string(), type))
                    in 400..499 -> entityCallback.onFailure("客户端错误")
                    in 501..599 -> entityCallback.onFailure("服务器错误")
                    else -> entityCallback.onFailure("未知错误")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                entityCallback.onFailure("网络异常");
            }
        })
    }
}