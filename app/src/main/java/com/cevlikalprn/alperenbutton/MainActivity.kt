package com.cevlikalprn.alperenbutton

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cevlikalprn.buttonalperen.ButtonAlper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAlper = findViewById<ButtonAlper>(R.id.buttonAlper)

        ButtonAlper.ButtonBuilder(this)
            .backgroundColor(resources.getColor(R.color.def_background_color))
            .rippleColor(resources.getColor(R.color.white))
            .radius(12f)
            .shadowHeight(20f)
            .shadowColor(resources.getColor(R.color.def_shadow_color))
            .build(btnAlper)
    }
}