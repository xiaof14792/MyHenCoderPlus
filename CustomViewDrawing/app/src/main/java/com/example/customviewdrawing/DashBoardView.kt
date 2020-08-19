package com.example.customviewdrawing

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 11-图形的位置和尺寸测量
 *
 * 绘制的基本要素
 * onDraw(Canvas)
 * Canvas
 * Paint
 * 坐标系
 * 尺寸单位
 *
 */
private const val OPEN_ANGLE = 120 //开角大小
private val DASH_WIDTH = 3f.px //刻度宽度
private val DASH_HEIGHT = 5f.px //刻度高度
private const val DASH_NUM = 20 //刻度数目
private val RADIUS = 150f.px //包括弧的正方形的半径
private val MARK = 5 //当前指针在第几个刻度

/**
 * 仪表盘图
 */
class DashBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path()
    val dashPath = Path()
    var pathEffect: PathEffect? = null

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f.px

        dashPath.addRect(0f, 0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        path.addArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            (360 - OPEN_ANGLE).toFloat()
        )

        val pathMeasure = PathMeasure(path, false)
        pathEffect = PathDashPathEffect(
            dashPath,
            (pathMeasure.length - DASH_WIDTH) / DASH_NUM,
            0f,
            PathDashPathEffect.Style.ROTATE
        )

    }

    override fun onDraw(canvas: Canvas) {
        //画弧
        canvas.drawPath(path, paint)

        //画刻度
        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null

        //画指针，使用了三角函数
        val angle = (90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20 * MARK).toDouble()
        canvas.drawLine(
            width / 2f,
            height / 2f,
            (width / 2f + RADIUS * 0.8 * Math.cos(Math.toRadians(angle))).toFloat(),
            (height / 2f + RADIUS * 0.8 * Math.sin(Math.toRadians(angle))).toFloat(),
            paint
        )

    }

}