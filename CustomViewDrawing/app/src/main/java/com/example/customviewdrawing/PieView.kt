package com.example.customviewdrawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

private val RADIUS = 150f.px
private val ANGLES = floatArrayOf(45f, 60f, 150f, 105f)
private val COLORS = intArrayOf(
    0xFFFF834E.toInt(), 0xFF54FFAD.toInt(),
    0xFF22B4FF.toInt(), 0xFFFF9EE7.toInt()
)
private const val SELECTED_INDEX = 2; //饼图选中的部分下标
private val OFFSET = 20f.px //选中部分偏差距离

/**
 * 饼图-带选中突出效果
 */
class PieView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    lateinit var rectF: RectF

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rectF =
            RectF(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var startAngle = 0f
        //遍历绘制每块弧形
        for (index: Int in ANGLES.indices) {
            paint.color = COLORS[index]
            if (index == SELECTED_INDEX) {
                //选中扇形突出效果
                val angle = startAngle + ANGLES[index] / 2 //取饼图某块扇形中间的角度

                canvas.save()
                //偏移坐标系
                canvas.translate(
                    (OFFSET * cos(Math.toRadians(angle.toDouble()))).toFloat(),
                    (OFFSET * sin(Math.toRadians(angle.toDouble()))).toFloat()
                )
                canvas.drawArc(rectF, startAngle, ANGLES[index], true, paint)
                canvas.restore()
            } else {
                canvas.drawArc(rectF, startAngle, ANGLES[index], true, paint)
            }

            startAngle += ANGLES[index] //更新起始角度
        }
    }

}