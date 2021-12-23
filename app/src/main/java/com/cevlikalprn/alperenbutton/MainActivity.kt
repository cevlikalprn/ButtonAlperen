package com.cevlikalprn.alperenbutton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cevlikalprn.buttonalperen.ButtonAlper

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
    }
}