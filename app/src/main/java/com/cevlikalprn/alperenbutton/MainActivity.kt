package com.cevlikalprn.alperenbutton

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cevlikalprn.buttonalperen.ButtonAlper
import com.cevlikalprn.buttonalperen.FoodOrder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAlper = findViewById<ButtonAlper>(R.id.buttonAlper)

        ButtonAlper.ButtonBuilder(this)
            .backgroundColor(
                resources.getColor(R.color.purple_200)
            )
            .radius(0f)
            .rippleColor(resources.getColor(R.color.black))
            .build(btnAlper)


/*
        btnAlper
            .changeRadius(8f)
            .changeBackgroundColor(resources.getColor(R.color.def_background_color))
            .changeShadowState(
                shadowColor = resources.getColor(R.color.def_shadow_color),
                shadowHeight = 16f
            )
            .changeRippleEffect(
                rippleColor = resources.getColor(R.color.white),
                rippleOpacity = 0.30f
            )

 */
    }
}