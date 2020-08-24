package com.example.customviewanimation.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviewanimation.R
import com.example.customviewanimation.dp

private val IMAGE_SIZE = 200.dp
private val IMAGE_PADDING = 100.dp

/**
 * "范围裁切和几何变换"
 * 东西不多但还是有难度的，重点掌握canvas Matrix camera的几何变换物理模型，代码倒着写会更方便人思考的技巧d
 */
class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val IMAGE = getAvatar(IMAGE_SIZE.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera().apply {
        //避免"糊脸效果"，且在不同手机上保持一致的UI效果
        setLocation(0f, 0f, -6 * Resources.getSystem().displayMetrics.density)
    }

    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bottomFlip = 30f
        set(value) {
            field = value
            invalidate()
        }
    var flipRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //带旋转的翻页效果
        //上半部分
        canvas.save() //避免范围裁切影响到后面的UI绘制
        canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        canvas.rotate(-flipRotation, 0f, 0f)
        camera.save()
        camera.rotateX(topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(
            -IMAGE_SIZE, -IMAGE_SIZE,
            IMAGE_SIZE, 0f //这里因为旋转后裁切，所以应扩大裁切范围
        )
        canvas.rotate(flipRotation, 0f, 0f)
        canvas.translate(-IMAGE_PADDING - IMAGE_SIZE / 2, -IMAGE_PADDING - IMAGE_SIZE / 2)
        canvas.drawBitmap(IMAGE,
            IMAGE_PADDING,
            IMAGE_PADDING, paint)
        canvas.restore()

        //下半部分
        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_SIZE / 2, IMAGE_PADDING + IMAGE_SIZE / 2)
        canvas.rotate(-flipRotation, 0f, 0f)
        camera.save()
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(
            -IMAGE_SIZE, 0f,
            IMAGE_SIZE,
            IMAGE_SIZE
        )
        canvas.rotate(flipRotation, 0f, 0f)
        canvas.translate(-IMAGE_PADDING - IMAGE_SIZE / 2, -IMAGE_PADDING - IMAGE_SIZE / 2)
        canvas.drawBitmap(IMAGE,
            IMAGE_PADDING,
            IMAGE_PADDING, paint)
        canvas.restore()
    }


    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources,
            R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources,
            R.drawable.avatar, options)
    }
}



