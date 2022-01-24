package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.carhire.Utils.SnackBarUtil

class ProfileActivity : AppCompatActivity() {
    lateinit var saveInfo : TextView
    lateinit var profileLayout : RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        profileLayout = findViewById(R.id.profile_layout)
        saveInfo = findViewById(R.id.save_button)
        saveInfo.setOnClickListener {
           SnackBarUtil().showSnackBar(this,"Information Saved",profileLayout)
        }
    }

    override fun onBackPressed() {
        val profileToMainIntent = Intent(this,MainActivity::class.java)
        startActivity(profileToMainIntent)
        finish()
    }
}