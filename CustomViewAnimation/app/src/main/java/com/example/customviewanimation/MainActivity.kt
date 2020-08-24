package com.example.customviewanimation

import android.animation.*
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * ViewPropertyAnimator
         */
        /*view.animate()
                .translationX(200.dp)
                .translationY(100.dp)
                .alpha(0.5f)
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setStartDelay(1000)*/

        /**
         * ObjectAnimator
         */
        /*val objectAnimator = ObjectAnimator.ofFloat(circleView, "radius", 150.dp)
        objectAnimator.duration = 500
        objectAnimator.startDelay = 1000
        objectAnimator.start()*/

        /**
         * AnimatorSet
         */
        /*var topFlipAnimator = ObjectAnimator.ofFloat(cameraView, "topFlip", -60f)
        topFlipAnimator.startDelay = 200
        topFlipAnimator.duration = 500

        var bottomFlipAnimator = ObjectAnimator.ofFloat(cameraView, "bottomFlip", 60f)
        bottomFlipAnimator.startDelay = 1000
        bottomFlipAnimator.duration = 500

        var flipRotationAnimator = ObjectAnimator.ofFloat(cameraView, "flipRotation", 270f)
        flipRotationAnimator.startDelay = 200
        flipRotationAnimator.duration = 500

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator)
        animatorSet.start()*/

        /**
         * PropertyValuesHolder //同时对一个View的多个属性做动画
         */
        /*val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -60f)
        val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 60f)
        val flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)

        val holdersAnimator = ObjectAnimator.ofPropertyValuesHolder(cameraView, topFlipHolder, bottomFlipHolder, flipRotationHolder)
        holdersAnimator.startDelay = 1000
        holdersAnimator.duration = 2000
        holdersAnimator.start()*/

        /**
         * KeyFrame //对同一个View的动画进行分段快慢的控制
         */
        /*val length = 200.dp
        val keyframe1 = Keyframe.ofFloat(0f, 0.0f * length)
        val keyframe2 = Keyframe.ofFloat(0.2f, 0.4f * length)
        val keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length)
        val keyframe4 = Keyframe.ofFloat(1.0f, 1.0f * length)
        val propertyValuesHolder = PropertyValuesHolder.ofKeyframe(
            "translationX",
            keyframe1,
            keyframe2,
            keyframe3,
            keyframe4
        )
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolder)
        objectAnimator.startDelay = 1000
        objectAnimator.duration = 1000
        objectAnimator.start()*/

        /**
         * Interpolator //4种常用的插值器
         */
        /*val objectAnimator = ObjectAnimator.ofFloat(5f)
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.interpolator = AccelerateInterpolator()
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.interpolator = LinearInterpolator()*/

        /**
         * TypeEvaluator //用于完成动画进度 到 具体属性值的映射
         */
        /*val pointFAnimator =
            ObjectAnimator.ofObject(pointFView, "pointF", PointFEvaluator(), PointF(200.dp, 100.dp))
        pointFAnimator.startDelay = 1000
        pointFAnimator.duration = 500
        pointFAnimator.start()*/

        val provinceAnimator =
            ObjectAnimator.ofObject(provinceView, "province", ProvinceEvaluator(), "澳门特别行政区")
        provinceAnimator.startDelay = 1000
        provinceAnimator.duration = 5000
        provinceAnimator.start()
    }

    class PointFEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            val startX = startValue.x
            val endX = endValue.x
            val currentX = startX + (endX - startX) * fraction
            val startY = startValue.y
            val endY = endValue.y
            val currentY = startY + (endY - startY) * fraction

            return PointF(currentX, currentY)
        }

    }

}