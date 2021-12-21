package com.cevlikalprn.alperenbutton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cevlikalprn.buttonalperen.ButtonAlper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAlper = findViewById<ButtonAlper>(R.id.buttonAlper)
/*
        btnAlper.apply {

            changeRadius(12f)

            changeBackgroundColor(resources.getColor(R.color.def_background_color))

            changeShadowState(
                shadowColor = resources.getColor(R.color.def_shadow_color),
                shadowHeight = 12f
            )

            changeRippleEffect(rippleColor = resources.getColor(R.color.white), rippleOpacity = 0.30f)

        }
        
 */
    }
}