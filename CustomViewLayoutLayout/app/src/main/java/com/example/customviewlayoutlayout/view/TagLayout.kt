package com.example.customviewlayoutlayout.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val bounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val specHeightSize = MeasureSpec.getSize(widthMeasureSpec)
        val specHeightMode = MeasureSpec.getMode(widthMeasureSpec)

        //布局最终需要的宽高度
        var widthUsed = 0
        var heightUsed = 0

        //当前最新行的宽高度
        var lineWidthUsed = 0
        var lineMaxHeight = 0

        for ((index, child) in children.withIndex()) {
            //调用系统固定算法，现根据布局的尺寸信息结合开发者对子View的要求得到childWidthMeasureSpec childHeightMeasureSpec
            //再调用子view的measure方法去测量子View
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                0,
                heightMeasureSpec,
                heightUsed
            )

            //自己写固定算法，测量子View
            /*var childWidthSpecSize = 0
            var childWidthSpecMode = 0
            var childHeightSpecSize = 0
            var childHeightSpecMode = 0

            val layoutParams = child.layoutParams
            when (layoutParams.width) {
                LayoutParams.MATCH_PARENT -> {
                    when (specWidthMode) {
                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                            childWidthSpecSize = specWidthSize - 0
                            childWidthSpecMode = MeasureSpec.EXACTLY
                        }
                        MeasureSpec.UNSPECIFIED -> {
                            childWidthSpecSize = 0
                            childWidthSpecMode = MeasureSpec.UNSPECIFIED
                        }
                    }
                }

                LayoutParams.WRAP_CONTENT -> {
                    when (specWidthMode) {
                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                            childWidthSpecSize = specWidthSize - 0
                            childWidthSpecMode = MeasureSpec.AT_MOST
                        }
                        MeasureSpec.UNSPECIFIED -> {
                            childWidthSpecSize = 0
                            childWidthSpecMode = MeasureSpec.UNSPECIFIED
                        }
                    }
                }

                else -> {
                    childWidthSpecSize = layoutParams.width
                    childWidthSpecMode = MeasureSpec.EXACTLY
                }
            }

            when (layoutParams.height) {
                LayoutParams.MATCH_PARENT -> {
                    when (specWidthMode) {
                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                            childHeightSpecSize = specHeightSize - heightUsed
                            childHeightSpecMode = MeasureSpec.EXACTLY
                        }
                        MeasureSpec.UNSPECIFIED -> {
                            childHeightSpecSize = 0
                            childHeightSpecMode = MeasureSpec.UNSPECIFIED
                        }
                    }
                }

                LayoutParams.WRAP_CONTENT -> {
                    when (specWidthMode) {
                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                            childHeightSpecSize = specHeightSize - heightUsed
                            childHeightSpecMode = MeasureSpec.AT_MOST
                        }
                        MeasureSpec.UNSPECIFIED -> {
                            childHeightSpecSize = 0
                            childHeightSpecMode = MeasureSpec.UNSPECIFIED
                        }
                    }
                }

                else -> {
                    childWidthSpecSize = layoutParams.height
                    childWidthSpecMode = MeasureSpec.EXACTLY
                }
            }

            child.measure(
                MeasureSpec.makeMeasureSpec(childWidthSpecSize, childWidthSpecMode),
                MeasureSpec.makeMeasureSpec(childHeightSpecSize, childHeightSpecMode)
            )*/


            //需要换行了
            if (lineWidthUsed + child.measuredWidth > specWidthSize && specWidthMode != MeasureSpec.UNSPECIFIED) {
                heightUsed += lineMaxHeight
                lineWidthUsed = 0
                lineMaxHeight = 0
            }

            if (index >= bounds.size) {
                bounds.add(Rect())
            }
            val bound = bounds[index]
            bound.set(
                lineWidthUsed,
                heightUsed,
                lineWidthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight
            )

            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
            lineWidthUsed += child.measuredWidth
            widthUsed = max(widthUsed, lineWidthUsed) //为什么要放这里？因为有可能不足一行，必须每次循环都要比较
        }

        val selfWidth = widthUsed
        val sefHeight = heightUsed + lineMaxHeight
        setMeasuredDimension(selfWidth, sefHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val bound = bounds[index]
            child.layout(bound.left, bound.top, bound.right, bound.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

}