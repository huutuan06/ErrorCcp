package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_english).setOnClickListener {
            setLocale(this, "en")
            restartActivity(this)
        }

        findViewById<Button>(R.id.btn_vietnamese).setOnClickListener {
            setLocale(this, "vi")
            restartActivity(this)
        }

        findViewById<Button>(R.id.btn_next).setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}