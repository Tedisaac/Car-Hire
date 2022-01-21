package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CarDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)
    }

    override fun onBackPressed() {
        val backToMain = Intent(this,MainActivity::class.java)
        startActivity(backToMain)
        finish()
    }
}