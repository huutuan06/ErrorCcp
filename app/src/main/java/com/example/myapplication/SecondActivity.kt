package com.example.myapplication

import android.os.Bundle
import android.widget.Button

class SecondActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<Button>(R.id.btn_english).setOnClickListener {
            setLocale(this, "en")
            restartActivity(this)
        }

        findViewById<Button>(R.id.btn_chinese).setOnClickListener {
            setLocale(this, "zh")
            restartActivity(this)
        }
    }
}
