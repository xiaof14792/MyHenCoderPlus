package com.example.customviewtext.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.customviewtext.dp
import org.w3c.dom.Text

private val RING_WIDTH = 20.dp
private val RADIUS = 150.dp
private val CIRCLE_COLOR = 0xffB8B6B7.toInt()
private val HIGHLIGHT_COLOR = 0xffFF834E.toInt()
private val TEXT_SIZE = 90.dp //这里绘制的文字，更偏向图像意义，只受到屏幕像素密度的影响，不受系统文字大小的控制
private val TEXT = "abab"


class SportsView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var bounds: RectF
    private val rect: Rect = Rect()
    private val fontMetrics = Paint.FontMetrics()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bounds =
            RectF(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制环
        paint.style = Paint.Style.STROKE
        paint.color = CIRCLE_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        //绘制进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(bounds, -60f, 150f, false, paint)

        //绘制文字
        paint.textSize = TEXT_SIZE
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.CENTER
        /*//纵向居中绘制文字
        paint.getTextBounds(TEXT, 0, TEXT.length, rect)
        //严格意义上（像素级别）的纵向居中，但只适合静态文字，如果是动态文字top和bottom会发生变化产生"文字跳动"问题
        canvas.drawText(TEXT, width / 2f, height / 2f - (rect.top + rect.bottom) / 2, paint)*/

        paint.getFontMetrics(fontMetrics)
        //适合动态文字的纵向居中，适合动态文字，不会产生"文字跳动"问题，但不是像素级的纵向居中，是"排版上"的居中
        canvas.drawText(
            TEXT,
            width / 2f,
            height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2, //减去文字中心的偏差
            paint
        )

        /**
         * 文字的贴边
         */
        //贴顶
        paint.textSize = TEXT_SIZE
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(TEXT, 0, TEXT.length, rect)
//        canvas.drawText(TEXT, 0f, (- rect.top).toFloat(), paint) //第一种方式
        paint.getFontMetrics(fontMetrics)
//        canvas.drawText(TEXT, 0f, -fontMetrics.ascent, paint) //第二种方式
        canvas.drawText(TEXT, 0f, -fontMetrics.top, paint) //第三种方式

        //贴底
//        canvas.drawText(TEXT, 0f, height.toFloat(), paint)
//        canvas.drawText(TEXT, 0f, height - fontMetrics.bottom, paint)
//        canvas.drawText(TEXT, 0f, height - fontMetrics.descent, paint)
        canvas.drawText(TEXT, 0f, (height - rect.bottom).toFloat(), paint) //同理也是4种方式

        //贴左边
        paint.textSize = 20.dp
        paint.getFontMetrics(fontMetrics)
//        canvas.drawText(TEXT, 0f, -fontMetrics.top, paint) //这种方式当相邻两行字体大小不一致时，效果会不一致，用TextBounds可以解决这个误差
        paint.getTextBounds(TEXT, 0, TEXT.length, rect)
        canvas.drawText(
            TEXT,
            (-rect.left).toFloat(),
            -fontMetrics.top,
            paint
        ) //字体大小不同时，上下两行效果还是有偏差，不能解决，因为系统并不知道有这个偏差，属于字体左边自带的缝隙

        //贴右边
        paint.textSize = TEXT_SIZE
        paint.getFontMetrics(fontMetrics)
        paint.getTextBounds(TEXT, 0, TEXT.length, rect)
        canvas.drawText(TEXT, (width - rect.right).toFloat(), -fontMetrics.top, paint)
    }
}