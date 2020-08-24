package com.example.customviewanimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.provider.CalendarContract
import android.util.AttributeSet
import android.view.View

class PointFView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        strokeWidth = 50.dp
        strokeCap = Paint.Cap.ROUND
    }
    var pointF = PointF(0f, 0f)
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPoint(pointF.x, pointF.y, paint)
    }
}