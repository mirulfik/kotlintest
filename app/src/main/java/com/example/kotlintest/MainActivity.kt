package com.example.kotlintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange)
        /** Splash screen **/
        handler = Handler()
        handler.postDelayed({
            val intent = Intent (this,MainMenu::class.java)
            startActivity(intent)
            finish()

        }, 3000)

    }
}