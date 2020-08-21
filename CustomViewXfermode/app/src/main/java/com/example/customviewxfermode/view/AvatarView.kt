package com.example.customviewxfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviewxfermode.R
import com.example.customviewxfermode.dp

private val IMAGE_SIZE = 150.dp
private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class AvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val bitmap = getAvatar(IMAGE_SIZE.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var bounds: RectF

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bounds = RectF(
            (width - IMAGE_SIZE) / 2,
            (height - IMAGE_SIZE) / 2,
            (width + IMAGE_SIZE) / 2,
            (height + IMAGE_SIZE) / 2
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val count = canvas.saveLayer(bounds, paint) //必须设置离屏缓冲，不然DST图形为整个屏幕图像，得不到想要的效果
        canvas.drawCircle(width / 2f, height / 2f, IMAGE_SIZE / 2, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(bitmap, (width - IMAGE_SIZE) / 2, (height - IMAGE_SIZE) / 2, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
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