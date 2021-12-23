package com.cevlikalprn.buttonalperen

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.cevlikalprn.buttonalperen.Constants.DEF_RADIUS
import com.cevlikalprn.buttonalperen.Constants.DEF_RIPPLE_OPACITY
import com.cevlikalprn.buttonalperen.Constants.DEF_SHADOW_HEIGHT
import kotlin.math.min
import kotlin.math.roundToInt

class ButtonAlper : AppCompatButton {

    private var gradientDrawable = GradientDrawable()
    private var maskGradientDrawable = GradientDrawable()
    private var buttonBackgroundColor: Int = resources.getColor(R.color.def_background_color)
    private var buttonRadius: Float = DEF_RADIUS
    private var buttonShadowColor: Int = resources.getColor(R.color.def_shadow_color)
    private var buttonShadowHeight: Float = DEF_SHADOW_HEIGHT
    private var buttonRippleColor: Int = resources.getColor(R.color.def_ripple_color)
    private var buttonRippleOpacity: Float = DEF_RIPPLE_OPACITY


    class ButtonBuilder(private val context: Context) {

        var buttonBackgroundColor: Int = R.color.def_background_color
            private set
        var buttonRadius: Float = DEF_RADIUS
            private set
        var buttonShadowColor: Int = R.color.def_shadow_color
            private set
        var buttonShadowHeight: Float = DEF_SHADOW_HEIGHT
            private set
        var buttonRippleColor: Int = R.color.def_ripple_color
            private set
        var buttonRippleOpacity: Float = DEF_RIPPLE_OPACITY
            private set

        fun backgroundColor(backgroundColor: Int) =
            apply { buttonBackgroundColor = backgroundColor }

        fun radius(radius: Float) =
            apply { buttonRadius = radius }

        fun shadowColor(shadowColor: Int) =
            apply { this.buttonShadowColor = shadowColor }

        fun shadowHeight(shadowHeight: Float) =
            apply { this.buttonShadowHeight = shadowHeight }

        fun rippleColor(rippleColor: Int) =
            apply { this.buttonRippleColor = rippleColor }

        fun rippleOpacity(rippleOpacity: Float) =
            apply { this.buttonRippleOpacity = rippleOpacity }

        fun build(view: ButtonAlper) =
            ButtonAlper(context, this, view)
    }

    constructor(context: Context, builder: ButtonBuilder, view: ButtonAlper) : super(context) {
        view.buttonBackgroundColor = builder.buttonBackgroundColor
        view.buttonRadius = builder.buttonRadius
        view.buttonShadowColor = builder.buttonShadowColor
        view.buttonShadowHeight = builder.buttonShadowHeight
        view.buttonRippleColor = builder.buttonRippleColor
        view.buttonRippleOpacity = builder.buttonRippleOpacity
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

            buttonBackgroundColor = typedArray.getColor(
                R.styleable.ButtonAlper_ab_bg_color,
                ContextCompat.getColor(context, R.color.def_background_color)
            )

            buttonShadowColor = typedArray.getColor(
                R.styleable.ButtonAlper_ab_shadow_color,
                ContextCompat.getColor(context, R.color.def_shadow_color)
            )

            buttonShadowHeight = typedArray.getDimension(
                R.styleable.ButtonAlper_ab_shadow_height,
                DEF_SHADOW_HEIGHT
            )

            buttonRadius = typedArray.getDimension(
                R.styleable.ButtonAlper_ab_radius,
                DEF_RADIUS
            )

            buttonRippleColor = typedArray.getColor(
                R.styleable.ButtonAlper_ab_ripple_color,
                ContextCompat.getColor(context, R.color.def_ripple_color)
            )

            buttonRippleOpacity = typedArray.getFloat(
                R.styleable.ButtonAlper_ab_ripple_opacity,
                DEF_RIPPLE_OPACITY
            )

            typedArray.recycle()
        }
        setButtonAttributes()
    }

    private fun setButtonAttributes() {

        gradientDrawable.cornerRadii = getFloatArray(buttonRadius)
        gradientDrawable.setColor(buttonBackgroundColor)

        gradientDrawable.constantState?.let {
            maskGradientDrawable = it.newDrawable() as GradientDrawable
            maskGradientDrawable.setColor(Color.WHITE)
        }

        val colorStateList = getColorStateList(buttonRippleColor, buttonRippleOpacity)


        val layerDrawable = layerDrawable(
            buttonBackgroundColor,
            buttonShadowColor,
            buttonShadowHeight,
            buttonRadius
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

    private fun getColorStateList(rippleColor: Int, rippleOpacity: Float): ColorStateList {
        return ColorStateList.valueOf(
            getColorForRipple(
                rippleColor,
                rippleOpacity
            )
        )
    }

    private fun getFloatArray(value: Float): FloatArray {
        return floatArrayOf(
            value,
            value,
            value,
            value,
            value,
            value,
            value,
            value
        )
    }

    private fun getColorForRipple(rippleColor: Int, opacity: Float): Int {

        return Color.argb(
            min((opacity * 255).roundToInt(), 255),
            min(Color.red(rippleColor).toFloat().roundToInt(), 255),
            min(Color.green(rippleColor).toFloat().roundToInt(), 255),
            min(Color.blue(rippleColor).toFloat().roundToInt(), 255)
        )
    }
/*
    fun changeBackgroundColor(color: Int): ButtonAlper {
        buttonBackgroundColor = color
        setButtonAttributes()
        return this
    }

    fun changeRadius(radius: Float): ButtonAlper {
        buttonRadius = radius
        setButtonAttributes()
        return this
    }

    fun changeShadowState(shadowColor: Int, shadowHeight: Float): ButtonAlper {

        buttonShadowColor = shadowColor
        buttonShadowHeight = shadowHeight
        setButtonAttributes()
        return this
    }

    fun changeRippleEffect(rippleColor: Int, rippleOpacity: Float): ButtonAlper {

        buttonRippleColor = rippleColor
        buttonRippleOpacity = rippleOpacity
        setButtonAttributes()
        return this
    }
 */
}