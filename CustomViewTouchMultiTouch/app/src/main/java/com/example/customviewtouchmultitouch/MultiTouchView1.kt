package com.example.customviewtouchmultitouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * 多点触控三种常见类型：接力型、配合（协作）型、各自为战型
 */
class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val bitmap = getAvatar(resources, 200.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var originalX = 0f
    private var originalY = 0f

    private var trackingPointerId = 0 //当前起作用的手指Pointer id

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    /**
     * 要点在于：始终找到起作用的那个Pointer，并追踪这个Pointer的坐标
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(0)

                downX = event.getX(0)
                downY = event.getY(0)

                originalX = offsetX
                originalY = offsetY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = event.actionIndex
                trackingPointerId = event.getPointerId(index)

                downX = event.getX(index)
                downY = event.getY(index)

                originalX = offsetX
                originalY = offsetY
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(trackingPointerId)

                offsetX = event.getX(index) - downX + originalX
                offsetY = event.getY(index) - downY + originalY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                var index = event.actionIndex
                if (index == event.pointerCount - 1){ //抬起的刚好是事件中最后一个pointer
                    index = event.pointerCount - 2
                }else{
                    index = event.pointerCount - 1
                }
                trackingPointerId = event.getPointerId(index)

                downX = event.getX(index)
                downY = event.getY(index)

                originalX = offsetX
                originalY = offsetY
                invalidate()
            }

        }
        return true
    }
}