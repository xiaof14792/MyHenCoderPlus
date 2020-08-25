package com.example.customviewlayoutsize

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

class SquareImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    //直接重写layout()固然可以达到改变View布局大小，但这种方式是父布局最后调用子View的layout()后子View不听话自己改变大小的方式，父布局并
    //不没有参与measure和layout，所以不知道子View的大小已经改变，会造成父ViewGroup的布局错误。正确的方式应该是重写onMeasure()
    /*override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, (l + 100.dp).toInt(), (t + 100.dp).toInt())
    }*/

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        val height = measuredHeight
        val size = min(measuredWidth, measuredHeight)

        setMeasuredDimension(size, size)
    }

}