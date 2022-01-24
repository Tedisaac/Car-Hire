package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.carhire.Utils.SnackBarUtil
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {
    lateinit var saveInfo : TextView
    lateinit var profileLayout : RelativeLayout
    lateinit var profilePageBack : CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        profilePageBack = findViewById(R.id.profile_page_back)
        profilePageBack.setOnClickListener {
            var profileToMain = Intent(this,MainActivity::class.java)
            startActivity(profileToMain)
            finish()
        }
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