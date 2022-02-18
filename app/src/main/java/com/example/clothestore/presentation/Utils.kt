package com.example.clothestore.presentation

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import timber.log.Timber
import java.math.RoundingMode
import java.text.DecimalFormat

object Utils {

    fun formatTotalPrice(price: Double): String {
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        return df.format(price)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun View.implementSpringAnimationTrait() {

        val scaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 0.90f)
        val scaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 0.90f)

        setOnTouchListener { v, event ->
            Timber.i(event.action.toString())
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    scaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                    scaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                    scaleXAnim.start()

                    scaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                    scaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                    scaleYAnim.start()

                }
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    scaleXAnim.cancel()
                    scaleYAnim.cancel()
                    val reverseScaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 1f)
                    reverseScaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                    reverseScaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                    reverseScaleXAnim.start()

                    val reverseScaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 1f)
                    reverseScaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                    reverseScaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                    reverseScaleYAnim.start()


                }
            }

            false
        }
    }


}



