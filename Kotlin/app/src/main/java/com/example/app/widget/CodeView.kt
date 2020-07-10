package com.example.app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.example.app.R
import com.example.core.utils.Utils
import com.example.core.utils.dp2px
import java.util.*

class CodeView : AppCompatTextView {

    constructor(context: Context): this(context, null){

    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs){
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        setGravity(Gravity.CENTER)
        setBackgroundColor(getContext().getColor(R.color.colorPrimary))
        setTextColor(Color.WHITE)

        paint.setAntiAlias(true)
        paint.setStyle(Paint.Style.STROKE)
        paint.setColor(getContext().getColor(R.color.colorAccent))
        paint.setStrokeWidth(dp2px(6f))

        updateCode()
    }

    private val paint = Paint()

    private val codeList = arrayOf(
            "kotlin",
            "android",
            "java",
            "http",
            "https",
            "okhttp",
            "retrofit",
            "tcp/ip"
    )

    fun updateCode(){
        val random = Random().nextInt(codeList.size)
        val code = codeList[random]
        setText(code)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawLine(0f, height.toFloat(), width.toFloat(), 0f, paint)
        super.onDraw(canvas)
    }
}