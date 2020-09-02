package com.example.customviewtouchscalableimageview

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import kotlin.math.max
import kotlin.math.min

private const val SCALE_MULTIPLEX = 1.5f //bigScale应该更大一点才能滑动

class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val bitmap = getAvatar(resources, 150.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //绘制图片中心初始偏差
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var smallScale = 0f
    private var bigScale = 0f

    private val henGestureListener = HenGestureListener()
    private val henScaleGestureListener = HenScaleGestureListener()

    private val scaleGestureDetector = ScaleGestureDetector(context, henScaleGestureListener)
    private val gestureDetector = GestureDetectorCompat(context, henGestureListener)

    //滑动的坐标偏差
    private var offsetX = 0f
    private var offsetY = 0f

    private var isBig = false

    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator: ObjectAnimator = ObjectAnimator()
    private val overScroller = OverScroller(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffsetX = (width - bitmap.width) / 2f
        originalOffsetY = (height - bitmap.height) / 2f

        //计算确定最小贴边比率和最大贴边比率
        if (width / height < bitmap.width / bitmap.height) {
            bigScale = height * 1.0f / bitmap.height * SCALE_MULTIPLEX
            smallScale = width * 1.0f / bitmap.width
        } else {
            smallScale = height * 1.0f / bitmap.height
            bigScale = width * 1.0f / bitmap.width * SCALE_MULTIPLEX
        }

        currentScale = smallScale //初始化缩放比值为小的贴边比值
        scaleAnimator.target = this
        scaleAnimator.setPropertyName("currentScale")
        scaleAnimator.setFloatValues(smallScale, bigScale)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction) //应该倒着写，坐标转换完后再去移动
        canvas.scale(
            currentScale,
            currentScale, width / 2f, height / 2f
        )
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress){
            gestureDetector.onTouchEvent(event)
        }

        return true
    }


    //配合OverScroller来实现滑动
    private fun refresh() {
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.currX.toFloat()
            offsetY = overScroller.currY.toFloat()
            invalidate()
            postOnAnimation { refresh() }
        }
    }

    inner class HenGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            downEvent: MotionEvent,
            currentEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (isBig) {
                overScroller.fling(
                    offsetX.toInt(), offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2).toInt(),
                    ((bitmap.width * bigScale - width) / 2).toInt(),
                    (-(bitmap.height * bigScale - height) / 2).toInt(),
                    ((bitmap.height * bigScale - height) / 2).toInt()
                )

                postOnAnimation { refresh() }
            }

            return false
        }

        override fun onScroll(
            downEvent: MotionEvent?,
            currentEvent: MotionEvent?,
            distanceX: Float,
            distanceY: Float //当前事件跟上个事件坐标点的差值（上个坐标减去这个坐标）
        ): Boolean {
            //图片放大时，跟随手指滑动
            if (isBig) {
                offsetX -= distanceX
                //调整坐标，防止滑动出现白边
                offsetX = max(
                    min(offsetX, (bitmap.width * bigScale - width) / 2f),
                    -(bitmap.width * bigScale - width) / 2f
                )

                offsetY -= distanceY
                offsetY = max(
                    min(offsetY, (bitmap.height * bigScale - height) / 2f),
                    -(bitmap.height * bigScale - height) / 2f
                )
                invalidate()
            }

            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            isBig = !isBig
            if (isBig) {
                //双击放大跟随手指
                offsetX = -(e.x - width / 2) * (bigScale / smallScale - 1)
                offsetY = -(e.y - height / 2) * (bigScale / smallScale - 1)

                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }

            return true
        }

    }

    inner class HenScaleGestureListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            //跟随两指中心放缩
            offsetX = -(detector.focusX - width / 2) * (bigScale / smallScale - 1)
            offsetY = -(detector.focusY - height / 2) * (bigScale / smallScale - 1)

            currentScale *= detector.scaleFactor
            currentScale = min(bigScale, max(currentScale, smallScale))

            return true //返回true代表消费这个事件，则返回放缩比为当前时刻和上一时刻的比值，否则为当前时刻和初始值的比值
        }
    }

}