package com.cevlikalprn.buttonalperen

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.cevlikalprn.buttonalperen.Constants.DEF_BOTTOM_PADDING
import com.cevlikalprn.buttonalperen.Constants.DEF_RADIUS
import com.cevlikalprn.buttonalperen.Constants.DEF_RIPPLE_OPACITY
import com.cevlikalprn.buttonalperen.Constants.DEF_SHADOW_HEIGHT
import com.cevlikalprn.buttonalperen.Constants.DEF_TOP_PADDING
import kotlin.math.min
import kotlin.math.roundToInt

class ButtonAlper : AppCompatButton {

    private var gradientDrawable = GradientDrawable()
    private var maskGradientDrawable = GradientDrawable()
    private var mBackgroundColor: Int = resources.getColor(R.color.def_background_color)
    private var mRadius: Float = DEF_RADIUS
    private var mShadowColor: Int = resources.getColor(R.color.def_shadow_color)
    private var mShadowHeight: Float = DEF_SHADOW_HEIGHT
    private var mRippleColor: Int = resources.getColor(R.color.def_ripple_color)
    private var mRippleOpacity: Float = DEF_RIPPLE_OPACITY

    class ButtonBuilder(private val context: Context) {

        var mBackgroundColor: Int = context.resources.getColor(R.color.def_background_color)
            private set
        var mRadius: Float = DEF_RADIUS
            private set
        var mShadowColor: Int = context.resources.getColor(R.color.def_shadow_color)
            private set
        var mShadowHeight: Float = DEF_SHADOW_HEIGHT
            private set
        var mRippleColor: Int = context.resources.getColor(R.color.def_ripple_color)
            private set
        var mRippleOpacity: Float = DEF_RIPPLE_OPACITY
            private set

        fun backgroundColor(backgroundColor: Int) =
            apply { mBackgroundColor = backgroundColor }

        fun radius(radius: Float) =
            apply { mRadius = radius }

        fun shadowColor(shadowColor: Int) =
            apply { this.mShadowColor = shadowColor }

        fun shadowHeight(shadowHeight: Float) =
            apply { this.mShadowHeight = shadowHeight }

        fun rippleColor(rippleColor: Int) =
            apply { this.mRippleColor = rippleColor }

        fun rippleOpacity(rippleOpacity: Float) =
            apply { this.mRippleOpacity = rippleOpacity }

        fun build(view: ButtonAlper) =
            ButtonAlper(context, this, view)
    }

    constructor(context: Context, builder: ButtonBuilder, view: ButtonAlper) : super(context) {
        view.mBackgroundColor = builder.mBackgroundColor
        view.mRadius = builder.mRadius
        view.mShadowColor = builder.mShadowColor
        view.mShadowHeight = builder.mShadowHeight
        view.mRippleColor = builder.mRippleColor
        view.mRippleOpacity = builder.mRippleOpacity
        view.setButtonAttributes()
    }

    constructor(context: Context) : super(context) {
        initButtonAttributes(null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initButtonAttributes(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initButtonAttributes(attributeSet)
    }


    private fun initButtonAttributes(attributeSet: AttributeSet?) {

        if (isInEditMode) {
            return
        }

        this.isClickable = true
        this.isAllCaps = false

        attributeSet?.let {

            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ButtonAlper)

            mBackgroundColor = typedArray.getColor(
                R.styleable.ButtonAlper_ab_bg_color,
                ContextCompat.getColor(context, R.color.def_background_color)
            )
            mShadowColor = typedArray.getColor(
                R.styleable.ButtonAlper_ab_shadow_color,
                ContextCompat.getColor(context, R.color.def_shadow_color)
            )
            mShadowHeight = typedArray.getDimension(
                R.styleable.ButtonAlper_ab_shadow_height,
                DEF_SHADOW_HEIGHT
            )
            mRadius = typedArray.getDimension(
                R.styleable.ButtonAlper_ab_radius,
                DEF_RADIUS
            )
            mRippleColor = typedArray.getColor(
                R.styleable.ButtonAlper_ab_ripple_color,
                ContextCompat.getColor(context, R.color.def_ripple_color)
            )
            mRippleOpacity = typedArray.getFloat(
                R.styleable.ButtonAlper_ab_ripple_opacity,
                DEF_RIPPLE_OPACITY
            )

            typedArray.recycle()
        }
        setButtonAttributes()
    }

    private fun setButtonAttributes() {

        gradientDrawable.cornerRadii = getFloatArray(mRadius)
        gradientDrawable.setColor(mBackgroundColor)

        setPadding(
            0,
            DEF_TOP_PADDING + mShadowHeight.toInt(),
            0,
            DEF_BOTTOM_PADDING + mShadowHeight.toInt()
        )

        gradientDrawable.constantState?.let {
            maskGradientDrawable = it.newDrawable() as GradientDrawable
            maskGradientDrawable.setColor(Color.WHITE)
        }

        val colorStateList = getColorStateList(mRippleColor, mRippleOpacity)


        val layerDrawable = layerDrawable(
            mBackgroundColor,
            mShadowColor,
            mShadowHeight,
            mRadius
        )

        val myDrawable = RippleDrawable(colorStateList, layerDrawable, maskGradientDrawable)

        background = myDrawable
    }

    private fun layerDrawable(
        backgroundColor: Int,
        shadowColor: Int,
        shadowHeight: Float,
        radius: Float
    ): Drawable {
        val outerRadii = getFloatArray(radius)

        val topRoundRect = RoundRectShape(outerRadii, null, null)
        val topShapeDrawable = ShapeDrawable(topRoundRect)
        topShapeDrawable.paint.color = backgroundColor

        val roundRectShape = RoundRectShape(outerRadii, null, null)
        val bottomShapeDrawable = ShapeDrawable(roundRectShape)
        bottomShapeDrawable.paint.color = shadowColor

        val layerDrawable = LayerDrawable(arrayOf(bottomShapeDrawable, topShapeDrawable))
        layerDrawable.setLayerInset(1, 0, 0, 0, shadowHeight.toInt())

        return layerDrawable
    }

    private fun getColorStateList(rippleColor: Int, rippleOpacity: Float) =
        ColorStateList.valueOf(
            getColorForRipple(
                rippleColor,
                rippleOpacity
            )
        )

    private fun getFloatArray(value: Float) = floatArrayOf(
        value,
        value,
        value,
        value,
        value,
        value,
        value,
        value
    )

    private fun getColorForRipple(rippleColor: Int, opacity: Float) =
        Color.argb(
            min((opacity * 255).roundToInt(), 255),
            min(Color.red(rippleColor).toFloat().roundToInt(), 255),
            min(Color.green(rippleColor).toFloat().roundToInt(), 255),
            min(Color.blue(rippleColor).toFloat().roundToInt(), 255)
        )
}