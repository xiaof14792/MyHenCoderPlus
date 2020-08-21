package com.example.customviewxfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)

class XfermodeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val BLUE = 0xff4A6AFF.toInt()
    private val PINK = 0xffFF50B4.toInt()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    //必须得到两个bitmap，包括透明的部分，然后再设置两个图片xfermode，才会得到正确的UI效果（保证融合效果的范围保持一致）
    //因为只是画一个圆和一个方形的话，融合范围不一致
    private val squareBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
    private val circleBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
    private val bounds = RectF(200f, 100f, 500f, 400f)

    init {
        val canvas = Canvas(squareBitmap)
        paint.color = BLUE
        canvas.drawRect(0f, 100f, 200f, 300f, paint)

        canvas.setBitmap(circleBitmap)
        paint.color = PINK
        canvas.drawOval(100f, 0f, 300f, 200f, paint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val count = canvas.saveLayer(bounds, paint)
        canvas.drawBitmap(circleBitmap, 200f, 100f, paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(squareBitmap, 200f, 100f, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }
}