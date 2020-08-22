package com.example.customviewclipandcamera.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviewclipandcamera.R
import com.example.customviewclipandcamera.dp

private val IMAGE_SIZE = 200.dp
private val IMAGE_PADDING = 100.dp

/**
 * "范围裁切和几何变换"
 * 东西不多但还是有难度的，重点掌握canvas Matrix camera的几何变换物理模型，代码倒着写会更方便人思考的技巧d
 */
class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val IMAGE = getAvatar(IMAGE_SIZE.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var clipRectF: RectF
    private val camera = Camera().apply {
        rotateX(30f)
        //避免"糊脸效果"，且在不同手机上保持一致的UI效果
        setLocation(0f, 0f, -6 * Resources.getSystem().displayMetrics.density)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        clipRectF = RectF(
            (width - IMAGE_SIZE) / 2f,
            (height - IMAGE_SIZE) / 2f,
            (width + IMAGE_SIZE) / 2f,
            height / 2f
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //范围裁切
        /*canvas.clipRect(clipRectF)
        canvas.drawBitmap(IMAGE, (width - IMAGE_SIZE) / 2f, (height - IMAGE_SIZE) / 2f, paint)*/

        /**
         * 因为几何变换（二维、三维）相关API均是改变坐标系，而人类的思维方式通常是以画的内容为主体思考，故使用各种API做几何变换时
         * 倒着写会更方便简单（保证正确的UI效果）
         */
        //三维旋转
        /*canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        camera.applyToCanvas(canvas)
        canvas.translate(-IMAGE_PADDING - IMAGE_SIZE / 2, -IMAGE_PADDING - IMAGE_SIZE / 2)
        canvas.drawBitmap(IMAGE, IMAGE_PADDING, IMAGE_PADDING, paint)*/

        //翻页效果
        //上半部分
        /*canvas.save() //避免范围裁切影响到后面的UI绘制
        canvas.clipRect(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_SIZE,
            IMAGE_PADDING + IMAGE_SIZE / 2
        )
        canvas.drawBitmap(IMAGE, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()

        //下半部分
        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        camera.applyToCanvas(canvas)
        canvas.translate(-IMAGE_PADDING - IMAGE_SIZE / 2, -IMAGE_PADDING - IMAGE_SIZE / 2)
        canvas.clipRect(
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_SIZE / 2,
            IMAGE_PADDING + IMAGE_SIZE,
            IMAGE_PADDING + IMAGE_SIZE
        )
        canvas.drawBitmap(IMAGE, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()*/

        //带旋转的翻页效果
        canvas.save() //避免范围裁切影响到后面的UI绘制
        canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        canvas.rotate(-30f, 0f, 0f)
        canvas.clipRect(
            -IMAGE_SIZE, -IMAGE_SIZE, IMAGE_SIZE, 0f //这里因为旋转后裁切，所以应扩大裁切范围
        )
        canvas.rotate(30f, 0f, 0f)
        canvas.translate(-IMAGE_PADDING - IMAGE_SIZE / 2, -IMAGE_PADDING - IMAGE_SIZE / 2)
        canvas.drawBitmap(IMAGE, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()

        //下半部分
        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        canvas.rotate(-30f, 0f, 0f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(
            -IMAGE_SIZE, 0f, IMAGE_SIZE, IMAGE_SIZE
        )
        canvas.rotate(30f, 0f, 0f)
        canvas.translate(-IMAGE_PADDING - IMAGE_SIZE / 2, -IMAGE_PADDING - IMAGE_SIZE / 2)
        canvas.drawBitmap(IMAGE, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
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

