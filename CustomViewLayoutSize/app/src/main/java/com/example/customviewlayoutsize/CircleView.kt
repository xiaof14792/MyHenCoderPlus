package com.example.customviewlayoutsize

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.security.MessageDigest
import kotlin.math.min

private val RADIUS = 100.dp
private val PADDING = 100.dp

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((RADIUS + PADDING) * 2).toInt()
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)

        val specHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        val specHeightSize = MeasureSpec.getSize(heightMeasureSpec)

        /*val width = when (specWidthMode) {
            MeasureSpec.EXACTLY -> specWidthSize
            MeasureSpec.AT_MOST -> min(size, specWidthSize)
            else -> size
        }

        val height = when (heightMeasureSpec) {
            MeasureSpec.EXACTLY -> specHeightSize
            MeasureSpec.AT_MOST -> min(size, specHeightSize)
            else -> size
        }*/

        //固定算法，安卓官方提供了简单方法直接调用
        val width = resolveSize(size, widthMeasureSpec)
        val height = resolveSize(size, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }
}