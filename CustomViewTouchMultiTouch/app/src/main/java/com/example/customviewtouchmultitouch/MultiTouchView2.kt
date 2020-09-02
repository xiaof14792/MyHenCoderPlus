package com.example.customviewtouchmultitouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * 多点触控三种常见类型：配合（协作）型
 * 要点在于 对于不同的点击事件：不断地计算得到多触点的虚拟中心点，然后对虚拟中心点进行跟踪
 */
class MultiTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val bitmap = getAvatar(resources, 200.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var originalX = 0f
    private var originalY = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //pointerUp事件回调时，仍然包括抬起的那个手指，在下一时刻的事件中才会把这个Pointer去掉，所以这里需要做一下过滤
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        var sumX = 0f
        var sumY = 0f
        for (i in 0 until event.pointerCount) {
            if (!(isPointerUp && i == event.actionIndex)) {
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }
        val pointerCount = if (isPointerUp) event.pointerCount - 1 else event.pointerCount
        val trackingX = sumX / pointerCount //多点触控时的虚拟中心点的坐标
        val trackingY = sumY / pointerCount

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP -> { //这3个事件回调时都需要对"落点"和"图片原始位置坐标"进行更新
                downX = trackingX
                downY = trackingY

                originalX = offsetX
                originalY = offsetY
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = trackingX - downX + originalX
                offsetY = trackingY - downY + originalY
                invalidate()
            }

        }
        return true
    }
}