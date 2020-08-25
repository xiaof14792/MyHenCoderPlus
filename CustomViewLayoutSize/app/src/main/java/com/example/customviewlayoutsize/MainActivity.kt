package com.example.customviewlayoutsize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * "自定义布局"
 *
 * 继承已有的View，简单改写他们的尺寸：重写onMeasure()
 * SquareImageView
 *
 * 对自定义View完全进行自定义尺寸计算：重写onMeasure()
 * CircleImage
 *
 * 自定义Layout：重写onMeasure()和onLayout()
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}