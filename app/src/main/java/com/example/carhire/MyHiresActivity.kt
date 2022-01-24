package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MyHiresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_hires)
    }

    override fun onBackPressed() {
        val myHiresToMainIntent = Intent(this,MainActivity::class.java)
        startActivity(myHiresToMainIntent)
        finish()
    }
}