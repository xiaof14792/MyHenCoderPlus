package com.example.okhttpcode

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException
import java.net.InetAddress
import kotlin.concurrent.thread
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://api.github.com/users/xiaof14792/repos"

        val client: OkHttpClient = OkHttpClient.Builder()
            .authenticator(object : Authenticator{
                override fun authenticate(route: Route?, response: Response): Request? {
                    if (response.code == 301){
                        //301认证失败，加入Header重新认证
                        return response.request.newBuilder()
                            .addHeader("bearer", "xxxxxx")
                            .build()
                    }

                    return response.request
                }

            })
            .cookieJar(CookieJar.NO_COOKIES)
            .cookieJar(object : CookieJar{
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    return emptyList()
                }

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                }

            })
            .eventListener(object : EventListener(){
                override fun requestBodyStart(call: Call) {
                    super.requestBodyStart(call)
                }

                override fun responseBodyEnd(call: Call, byteCount: Long) {
                    super.responseBodyEnd(call, byteCount)
                }
            })
            .build()


        val request: Request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request)
            .enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
//                client.dispatcher.executorService.shutdown()
            }

            override fun onResponse(call: Call, response: Response) {
                println("Response code_1: " + response.code)
            }

        })

        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    println("Response code_2: " + response.code)
                }

            })

        thread {
            //适用Java自带API获取域名IP地址
            println("DNS address: ${InetAddress.getAllByName("baidu.com").toList()[0].hostAddress}")
        }
    }


}

@ExperimentalContracts
fun test(isGo: Boolean){
    contract {
        returns() implies isGo
    }
}