package com.example.customviewbitmapanddrawble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Bitmap 和 Drawble
 * 什么是Bitmap
 * 什么是Drawable
 * Bitmap和Drawable互转的本质是什么
 * 自定义Drawable
 * 自定义Drawable的作用： 复用「绘制逻辑」
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val bitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888)
        bitmap.toDrawable(resources)
        val drawable = ColorDrawable(Color.RED)
        drawable.toBitmap()*/
    }
}