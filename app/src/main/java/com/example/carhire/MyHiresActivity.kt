package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.hdodenhof.circleimageview.CircleImageView

class MyHiresActivity : AppCompatActivity() {
    lateinit var myHiresBack : CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_hires)

        myHiresBack = findViewById(R.id.my_hires_back)
        myHiresBack.setOnClickListener {
            val myHiresToMain = Intent(this,MainActivity::class.java)
            startActivity(myHiresToMain)
            finish()
        }
    }

    override fun onBackPressed() {
        val myHiresToMainIntent = Intent(this,MainActivity::class.java)
        startActivity(myHiresToMainIntent)
        finish()
    }
}