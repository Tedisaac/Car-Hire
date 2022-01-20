package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout

class SignInActivity : AppCompatActivity() {
    lateinit var emailTextSignIn : EditText
    lateinit var passwordSignIn : EditText
    lateinit var signInLayout : RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInLayout = findViewById(R.id.sign_in_layout)
        signInLayout.setOnClickListener {
            var signInIntent  = Intent(this,MainActivity::class.java)
            startActivity(signInIntent)
            finish()
        }
    }
}