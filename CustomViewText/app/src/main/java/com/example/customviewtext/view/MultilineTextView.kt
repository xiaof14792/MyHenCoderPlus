package com.example.customviewtext.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.customviewtext.R
import com.example.customviewtext.dp

private val text =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ut ipsum et mi pharetra pulvinar. Suspendisse et odio ut odio ultricies euismod. Vestibulum risus arcu, fermentum eget semper non, laoreet id mauris. Donec egestas ipsum sollicitudin, volutpat enim a, lacinia ligula. Aenean elit nisl, pharetra sed egestas eget, facilisis sed urna. Aenean vel nisl nibh. Etiam quis tincidunt ante. In consequat facilisis ultrices. Ut dictum leo lorem. Nulla tempor elit a nisi pretium finibus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae;"
private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
    textSize = 16.dp
}
private val IMAGE_SIZE = 150.dp
private val IMAGE_PADDING = 50.dp

class MultilineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val fontMetrics = Paint.FontMetrics()
    private val bitmap = getAvatar(150.dp.toInt())

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /*val staticLayout = StaticLayout(
            text,
            0,
            text.length,
            paint,
            width,
            Layout.Alignment.ALIGN_NORMAL,
            1f,
            0f,
            false
        )
        staticLayout.draw(canvas)*/

        canvas.drawBitmap(bitmap, width - IMAGE_SIZE, IMAGE_PADDING, paint)

        paint.getFontMetrics(fontMetrics)
        val floatArray = floatArrayOf(0f)
        var start = 0
        var offsetY = -fontMetrics.top
        var count = 0
        var maxWidth = 0f
        while (start < text.length) {
            //动态避开图片
            //这里恰恰相反，文字排版是要去"拥抱偏差"的，加上偏差的效果后再去计算是否覆盖图片
            if (offsetY + fontMetrics.bottom < IMAGE_PADDING || offsetY + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE) {
                maxWidth = width.toFloat()
            } else {
                maxWidth = width.toFloat() - IMAGE_SIZE
            }
            //文字多行绘制
            count = paint.breakText(
                text,
                start,
                text.length,
                true,
                maxWidth,
                floatArray
            )
            canvas.drawText(text, start, start + count, 0f, offsetY, paint)

            start += count
            offsetY += paint.fontSpacing
        }

    }

    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
    }
}