package com.aiden.views.morphingbuttonlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.util.StateSet
import android.widget.Button


/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/7/12 15:04
 * package：com.aiden.views.morphingbuttonlibrary
 * version：1.0
 * <p>description：              </p>
 */
/**
 * 你可以在init代码块里面获得构造函数的传参，当然你也可以直接在声明属性的时候获得，
 * @JvmOverloads 如果你没有加上这个注解，它只能重载相匹配的的构造函数，而不是全部。
 */
open class MorphingButton : Button {

    private var mPadding: Padding? = null
    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private var mColor: Int = 0
    private var mCornerRadius: Int = 0
    private var mStrokeWidth: Int = 0
    private var mStrokeColor: Int = 0

    protected var mAnimationInProgress: Boolean = false

    var mDrawableNormal: StrokeGradientDrawable? = null
        private set
    private var mDrawablePressed: StrokeGradientDrawable? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mHeight == 0 && mWidth == 0 && w != 0 && h != 0) {
            mHeight = height
            mWidth = width
        }
    }

    open fun getDrawableNormal(): StrokeGradientDrawable {
        return mDrawableNormal!!
    }
    open fun morph(params: Params) {
        if (!mAnimationInProgress) {

            mDrawablePressed!!.setColor(params.colorPressed)
            mDrawablePressed!!.setCornerRadius(params.cornerRadius.toFloat())
            mDrawablePressed!!.setStrokeColor(params.strokeColor)
            mDrawablePressed!!.setStrokeWidth(params.strokeWidth)

            if (params.duration == 0) {
                morphWithoutAnimation(params)
            } else {
                morphWithAnimation(params)
            }

            mColor = params.color
            mCornerRadius = params.cornerRadius
            mStrokeWidth = params.strokeWidth
            mStrokeColor = params.strokeColor
        }
    }

    private fun morphWithAnimation(params: Params) {
        mAnimationInProgress = true
        text = null
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        setPadding(mPadding!!.left, mPadding!!.top, mPadding!!.right, mPadding!!.bottom)

        val animationParams = MorphingAnimation.Params.create(this)
            .color(mColor, params.color)
            .cornerRadius(mCornerRadius, params.cornerRadius)
            .strokeWidth(mStrokeWidth, params.strokeWidth)
            .strokeColor(mStrokeColor, params.strokeColor)
            .height(height, params.height)
            .width(width, params.width)
            .duration(params.duration)
            .listener(object : MorphingAnimation.Listener {
                override fun onAnimationEnd() {
                    finalizeMorphing(params)
                }
            })

        val animation = MorphingAnimation(animationParams)
        animation.start()
    }

    private fun morphWithoutAnimation(params: Params) {
        mDrawableNormal!!.setColor(params.color)
        mDrawableNormal!!.setCornerRadius(params.cornerRadius.toFloat())
        mDrawableNormal!!.setStrokeColor(params.strokeColor)
        mDrawableNormal!!.setStrokeWidth(params.strokeWidth)

        if (params.width != 0 && params.height != 0) {
            val layoutParams = layoutParams
            layoutParams.width = params.width
            layoutParams.height = params.height
            setLayoutParams(layoutParams)
        }

        finalizeMorphing(params)
    }

    private fun finalizeMorphing(params: Params) {
        mAnimationInProgress = false

        if (params.icon != 0 && params.text != null) {
            setIconLeft(params.icon)
            text = params.text
        } else if (params.icon != 0) {
            setIcon(params.icon)
        } else if (params.text != null) {
            text = params.text
        }

        if (params.animationListener != null) {
            params.animationListener!!.onAnimationEnd()
        }
    }

    fun blockTouch() {
        setOnTouchListener { v, event -> true }
    }

    fun unblockTouch() {
        setOnTouchListener { v, event -> false }
    }

    private fun initView() {
        mPadding = Padding()
        mPadding!!.left = paddingLeft
        mPadding!!.right = paddingRight
        mPadding!!.top = paddingTop
        mPadding!!.bottom = paddingBottom

        val resources = resources
        val cornerRadius = resources.getDimension(R.dimen.default_cornerRadius).toInt()
        val blue = resources.getColor(R.color.mb_blue)
        val blueDark = resources.getColor(R.color.mb_blue_dark)

        val background = StateListDrawable()
        mDrawableNormal = createDrawable(blue, cornerRadius, 0)
        mDrawablePressed = createDrawable(blueDark, cornerRadius, 0)

        mColor = blue
        mStrokeColor = blue
        mCornerRadius = cornerRadius

        background.addState(intArrayOf(android.R.attr.state_pressed), mDrawablePressed!!.getGradientDrawable())
        background.addState(StateSet.WILD_CARD, mDrawableNormal!!.getGradientDrawable())

        setBackgroundCompat(background)
    }

    private fun createDrawable(color: Int, cornerRadius: Int, strokeWidth: Int): StrokeGradientDrawable {
        val drawable = StrokeGradientDrawable(GradientDrawable())
        drawable.getGradientDrawable().shape = GradientDrawable.RECTANGLE
        drawable.setColor(color)
        drawable.setCornerRadius(cornerRadius.toFloat())
        drawable.setStrokeColor(color)
        drawable.setStrokeWidth(strokeWidth)

        return drawable
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setBackgroundCompat(drawable: Drawable?) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable)
        } else {
            background = drawable
        }
    }

    fun setIcon(@DrawableRes icon: Int) {
        // post is necessary, to make sure getWidth() doesn't return 0
        post {
            val drawable = resources.getDrawable(icon)
            val padding = width / 2 - drawable.intrinsicWidth / 2
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            setPadding(padding, 0, 0, 0)
        }
    }

    fun setIconLeft(@DrawableRes icon: Int) {
        //可以在上、下、左、右设置图标，如果不想在某个地方显示，则设置为null。图标的宽高将会设置为固有宽高，既自动通过getIntrinsicWidth和getIntrinsicHeight获取。
        setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
    }

    private inner class Padding {
        var left: Int = 0
        var right: Int = 0
        var top: Int = 0
        var bottom: Int = 0
    }

    class Params private constructor() {
        internal var cornerRadius: Int = 0
        internal var width: Int = 0
        internal var height: Int = 0
        internal var color: Int = 0
        internal var colorPressed: Int = 0
        internal var duration: Int = 0
        internal var icon: Int = 0
        internal var strokeWidth: Int = 0
        internal var strokeColor: Int = 0
        internal var text: String? = null
        internal var animationListener: MorphingAnimation.Listener? = null

        fun text(text: String): Params {
            this.text = text
            return this
        }

        fun icon(@DrawableRes icon: Int): Params {
            this.icon = icon
            return this
        }

        fun cornerRadius(cornerRadius: Int): Params {
            this.cornerRadius = cornerRadius
            return this
        }

        fun width(width: Int): Params {
            this.width = width
            return this
        }

        fun height(height: Int): Params {
            this.height = height
            return this
        }

        fun color(color: Int): Params {
            this.color = color
            return this
        }

        fun colorPressed(colorPressed: Int): Params {
            this.colorPressed = colorPressed
            return this
        }

        fun duration(duration: Int): Params {
            this.duration = duration
            return this
        }

        fun strokeWidth(strokeWidth: Int): Params {
            this.strokeWidth = strokeWidth
            return this
        }

        fun strokeColor(strokeColor: Int): Params {
            this.strokeColor = strokeColor
            return this
        }

        fun animationListener(animationListener: MorphingAnimation.Listener): Params {
            this.animationListener = animationListener
            return this
        }

        companion object {

            fun create(): Params {
                return Params()
            }
        }
    }
}