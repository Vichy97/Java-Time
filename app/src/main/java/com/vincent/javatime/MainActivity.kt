package com.vincent.javatime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_JavaTime)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}
