package com.example.customviewbitmapanddrawble

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val drawable = MeshDrawable()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
    }
}