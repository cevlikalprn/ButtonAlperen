package com.cevlikalprn.buttonalperen

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
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
    private var buttonBackgroundColor: Int? = null
    private var buttonRadius: Float? = null
    private var buttonShadowColor: Int? = null
    private var buttonShadowHeight: Float? = null
    private var buttonRippleColor: Int? = null
    private var buttonRippleOpacity: Float? = null


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

        gradientDrawable.cornerRadii = getFloatArray(buttonRadius!!)
        gradientDrawable.setColor(buttonBackgroundColor!!)

        gradientDrawable.constantState?.let {
            maskGradientDrawable = it.newDrawable() as GradientDrawable
            maskGradientDrawable.setColor(Color.WHITE)
        }

        val colorStateList = getColorStateList(buttonRippleColor!!, buttonRippleOpacity!!)


        val layerDrawable = layerDrawable(
            buttonBackgroundColor!!,
            buttonShadowColor!!,
            buttonShadowHeight!!,
            buttonRadius!!
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
}