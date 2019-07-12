package com.aiden.views.morphingbuttonlibrary

import android.graphics.drawable.GradientDrawable

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/7/12 15:47
 * package：com.aiden.views.morphingbuttonlibrary
 * version：1.0
 * <p>description：  drawable的包裹类            </p>
 */
class StrokeGradientDrawable {

    private var mStrokeWidth: Int = 0
    private var mStrokeColor: Int = 0

    private val mGradientDrawable: GradientDrawable
    private var mRadius: Float = 0.toFloat()
    private var mColor: Int = 0

    constructor(mGradientDrawable: GradientDrawable) {
        this.mGradientDrawable = mGradientDrawable
    }

    fun getStrokeWidth(): Int {
        return mStrokeWidth
    }

    fun setStrokeWidth(strokeWidth: Int) {
        mStrokeWidth = strokeWidth
        mGradientDrawable.setStroke(strokeWidth, getStrokeColor())
    }

    fun getStrokeColor(): Int {
        return mStrokeColor
    }

    fun setStrokeColor(strokeColor: Int) {
        mStrokeColor = strokeColor
        mGradientDrawable.setStroke(getStrokeWidth(), strokeColor)
    }

    fun setCornerRadius(radius: Float) {
        mRadius = radius
        mGradientDrawable.cornerRadius = radius
    }

    fun setColor(color: Int) {
        mColor = color
        mGradientDrawable.setColor(color)
    }

    fun getColor(): Int {
        return mColor
    }

    fun getRadius(): Float {
        return mRadius
    }

    fun getGradientDrawable(): GradientDrawable {
        return mGradientDrawable
    }

}