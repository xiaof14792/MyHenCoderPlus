package com.example.customviewtouchviewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

class TwoPager(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val velocityTracker = VelocityTracker.obtain()
    private val viewConfiguration = ViewConfiguration.get(context)
    private val maximumVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private val minimumVelocity = viewConfiguration.scaledMinimumFlingVelocity
    private val pagingTouchSlop = viewConfiguration.scaledPagingTouchSlop

    private val overScroller = OverScroller(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        val top = 0
        var right = width
        val bottom = height

        for (child in children) {
            child.layout(left, top, right, bottom)
            left += width
            right += width
        }
    }

    private var downX = 0f
    private var downY = 0f
    private var mScrollX = 0
    private var mScrollY = 0
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                mScrollX = scrollX
                mScrollY = scrollY
            }
            MotionEvent.ACTION_MOVE -> {
                if (abs((event.x - downX)) > pagingTouchSlop) {
                    //决定拦截事件流，同时也应该防止父View拦截事件流
                    requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                mScrollX = scrollX
            }
            MotionEvent.ACTION_MOVE -> {
                var scrollToX = (mScrollX - (event.x - downX)).toInt() //scroll方向和坐标系刚好相反
                scrollToX = scrollToX
                    .coerceAtLeast(0)
                    .coerceAtMost(width)
                scrollTo(scrollToX, mScrollY)

                postInvalidateOnAnimation()
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(
                    1000,
                    maximumVelocity.toFloat()
                )
                val currentXVelocity = velocityTracker.xVelocity

                var pageTarget = 0
                if (abs(currentXVelocity) > minimumVelocity) {
                    //如果Fling速度大于阈值，先根据速度来判断
                    //手指向右滑，内容向右移动，scorller坐标向左移动
                    pageTarget = if (currentXVelocity > 0) {
                        0
                    } else {
                        1
                    }
                } else {
                    //再根据滑动距离来判断，落到哪一页
                    pageTarget = if (scrollX > width / 2) {
                        1
                    } else {
                        0
                    }
                }

                val scrollXDistance = pageTarget * width - scrollX //计算抬起后需要滑动的距离
                overScroller.startScroll(scrollX, scrollY, scrollXDistance, 0)

                postInvalidateOnAnimation()
            }
        }

        return event.actionMasked == MotionEvent.ACTION_DOWN
    }

    //需要重写这个方法配合刷新来实现滑动的动画
    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}