package com.example.customviewlayoutlayout.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.customviewlayoutlayout.dp
import java.util.*

class ColoredTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private val COLORS = intArrayOf(
        0xff38B855.toInt(),
        0xffA0B820.toInt(),
        0xffB8772F.toInt(),
        0xffB83321.toInt(),
        0xffB83C71.toInt(),
        0xffB849AC.toInt(),
        0xff3C40B8.toInt()
    )

    private val TEXT_SIZE = intArrayOf(16, 22, 28)
    private val CORNER_RADIUS = 4.dp
    private val X_PADDING = 8.dp
    private val Y_PADDING = 16.dp

    private val random = Random()

    init {
        setTextColor(Color.WHITE)
        setTextSize(TEXT_SIZE[random.nextInt(3)].toFloat())

        paint.color = COLORS[random.nextInt(COLORS.size)]
        setPadding(X_PADDING.toInt(), Y_PADDING.toInt(), X_PADDING.toInt(), Y_PADDING.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            CORNER_RADIUS,
            CORNER_RADIUS,
            paint
        )

        super.onDraw(canvas)
    }
}