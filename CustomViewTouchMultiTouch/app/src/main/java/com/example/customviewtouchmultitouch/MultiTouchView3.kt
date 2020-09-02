package com.example.customviewtouchmultitouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View

/**
 * 各自为战型：使用Map来记录不同手指对应的path，为了方便追踪pointer，key设置为pointer的id
 */
class MultiTouchView3(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4.dp
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    private val path = Path()
    private var paths = SparseArray<Path>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0 until paths.size()){
            canvas.drawPath(paths.valueAt(i), paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val index = event.actionIndex
                val pointerId = event.getPointerId(index)
                val path = Path()
                path.moveTo(event.getX(index), event.getY(index))
                paths.append(pointerId, path)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> { //不需要找到移动的那个手指，事实上View上的每个pointer都在不断地move，直接遍历更新每个pointer的坐标
                for (index in 0 until event.pointerCount){
                    val id = event.getPointerId(index)
                    val path = paths[id]
                    path.lineTo(event.getX(index), event.getY(index))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                val index = event.actionIndex
                val pointerId = event.getPointerId(index)
                paths.remove(pointerId)
                invalidate()
            }
        }
        return true
    }
}