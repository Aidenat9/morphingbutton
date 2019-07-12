package com.aiden.views.morphingbuttonlibrary

import android.animation.*

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/7/12 16:36
 * package：com.aiden.views.morphingbuttonlibrary
 * version：1.0
 * <p>description：    属性动画包裹          </p>
 */
class MorphingAnimation(private val mParams: Params) {

    interface Listener {
        fun onAnimationEnd()
    }

    class Params private constructor(internal val button: MorphingButton) {

        internal var fromCornerRadius: Float = 0.toFloat()
        internal var toCornerRadius: Float = 0.toFloat()

        internal var fromHeight: Int = 0
        internal var toHeight: Int = 0

        internal var fromWidth: Int = 0
        internal var toWidth: Int = 0

        internal var fromColor: Int = 0
        internal var toColor: Int = 0

        internal var duration: Int = 0

        internal var fromStrokeWidth: Int = 0
        internal var toStrokeWidth: Int = 0

        internal var fromStrokeColor: Int = 0
        internal var toStrokeColor: Int = 0
        internal var animationListener: MorphingAnimation.Listener? = null

        fun duration(duration: Int): Params {
            this.duration = duration
            return this
        }

        fun listener(animationListener: MorphingAnimation.Listener): Params {
            this.animationListener = animationListener
            return this
        }

        fun color(fromColor: Int, toColor: Int): Params {
            this.fromColor = fromColor
            this.toColor = toColor
            return this
        }

        fun cornerRadius(fromCornerRadius: Int, toCornerRadius: Int): Params {
            this.fromCornerRadius = fromCornerRadius.toFloat()
            this.toCornerRadius = toCornerRadius.toFloat()
            return this
        }

        fun height(fromHeight: Int, toHeight: Int): Params {
            this.fromHeight = fromHeight
            this.toHeight = toHeight
            return this
        }

        fun width(fromWidth: Int, toWidth: Int): Params {
            this.fromWidth = fromWidth
            this.toWidth = toWidth
            return this
        }

        fun strokeWidth(fromStrokeWidth: Int, toStrokeWidth: Int): Params {
            this.fromStrokeWidth = fromStrokeWidth
            this.toStrokeWidth = toStrokeWidth
            return this
        }

        fun strokeColor(fromStrokeColor: Int, toStrokeColor: Int): Params {
            this.fromStrokeColor = fromStrokeColor
            this.toStrokeColor = toStrokeColor
            return this
        }

        companion object {

            fun create(button: MorphingButton): Params {
                return Params(button)
            }
        }

    }

    fun start() {
        val background = mParams.button.getDrawableNormal()

        val cornerAnimation =
            ObjectAnimator.ofFloat(background, "cornerRadius", mParams.fromCornerRadius, mParams.toCornerRadius)

        val strokeWidthAnimation =
            ObjectAnimator.ofInt(background, "strokeWidth", mParams.fromStrokeWidth, mParams.toStrokeWidth)

        val strokeColorAnimation =
            ObjectAnimator.ofInt(background, "strokeColor", mParams.fromStrokeColor, mParams.toStrokeColor)
        strokeColorAnimation.setEvaluator(ArgbEvaluator())

        val bgColorAnimation = ObjectAnimator.ofInt(background, "color", mParams.fromColor, mParams.toColor)
        bgColorAnimation.setEvaluator(ArgbEvaluator())

        val heightAnimation = ValueAnimator.ofInt(mParams.fromHeight, mParams.toHeight)
        heightAnimation.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = mParams.button.layoutParams
            layoutParams.height = `val`
            mParams.button.layoutParams = layoutParams
        }

        val widthAnimation = ValueAnimator.ofInt(mParams.fromWidth, mParams.toWidth)
        widthAnimation.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = mParams.button.layoutParams
            layoutParams.width = `val`
            mParams.button.layoutParams = layoutParams
        }

        val animatorSet = AnimatorSet()
        animatorSet.duration = mParams.duration.toLong()
        animatorSet.playTogether(
            strokeWidthAnimation, strokeColorAnimation, cornerAnimation, bgColorAnimation,
            heightAnimation, widthAnimation
        )
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (mParams.animationListener != null) {
                    mParams.animationListener!!.onAnimationEnd()
                }
            }
        })
        animatorSet.start()
    }

}
