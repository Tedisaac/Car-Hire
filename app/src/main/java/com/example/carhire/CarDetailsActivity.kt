package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.hdodenhof.circleimageview.CircleImageView

class CarDetailsActivity : AppCompatActivity() {
    lateinit var carDetailsBack : CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        carDetailsBack = findViewById(R.id.car_details_back)
        carDetailsBack.setOnClickListener {
            carDetailsBackFun()

        }
    }

    private fun carDetailsBackFun() {
        val backToMain = Intent(this,MainActivity::class.java)
        startActivity(backToMain)
        finish()
    }

    override fun onBackPressed() {
       carDetailsBackFun()
    }
}