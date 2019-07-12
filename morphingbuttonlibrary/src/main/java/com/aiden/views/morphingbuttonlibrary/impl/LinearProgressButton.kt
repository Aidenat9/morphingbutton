package com.aiden.views.morphingbuttonlibrary.impl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import com.aiden.views.morphingbuttonlibrary.IProgress
import com.aiden.views.morphingbuttonlibrary.MorphingButton

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/7/12 16:56
 * package：com.aiden.views.morphingbuttonlibrary.impl
 * version：1.0
 * <p>description：              </p>
 */
class LinearProgressButton : MorphingButton, IProgress {

    private var mProgress: Int = 0
    private var mProgressColor: Int = 0
    private var mProgressCornerRadius: Int = 0
    private var mPaint: Paint? = null
    private var mRectF: RectF? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!mAnimationInProgress && mProgress > MIN_PROGRESS && mProgress <= MAX_PROGRESS) {
            if (mPaint == null) {
                mPaint = Paint()
                mPaint!!.isAntiAlias = true
                mPaint!!.style = Paint.Style.FILL
                mPaint!!.color = mProgressColor
            }

            if (mRectF == null) {
                mRectF = RectF()
            }

            mRectF!!.right = (getWidth() / MAX_PROGRESS * mProgress).toFloat()
            mRectF!!.bottom = getHeight().toFloat()
            canvas.drawRoundRect(mRectF!!, mProgressCornerRadius.toFloat(), mProgressCornerRadius.toFloat(), mPaint!!)
        }
    }

    override fun morph(params: Params) {
        super.morph(params)
        mProgress = MIN_PROGRESS
        mPaint = null
        mRectF = null
    }

    override fun setProgress(progress: Int) {
        mProgress = progress
        invalidate()
    }

    fun morphToProgress(
        color: Int,
        progressColor: Int,
        progressCornerRadius: Int,
        width: Int,
        height: Int,
        duration: Int
    ) {
        mProgressCornerRadius = progressCornerRadius
        mProgressColor = progressColor

        val longRoundedSquare = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(mProgressCornerRadius)
            .width(width)
            .height(height)
            .color(color)
            .colorPressed(color)
        morph(longRoundedSquare)
    }

    companion object {

        val MAX_PROGRESS = 100
        val MIN_PROGRESS = 0
    }


}
