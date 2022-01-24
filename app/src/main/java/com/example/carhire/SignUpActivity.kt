package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RelativeLayout
import de.hdodenhof.circleimageview.CircleImageView

class SignUpActivity : AppCompatActivity() {
    lateinit var usernameSignUp : EditText
    lateinit var emailSignUp : EditText
    lateinit var passwordSignUp : EditText
    lateinit var confirmPasswordSignUp : EditText
    lateinit var showPasswordSignUp : CheckBox
    lateinit var signUpButton : Button
    lateinit var signUpBack : CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpBack = findViewById(R.id.sign_up_back)
        signUpBack.setOnClickListener {
            val signUpToSignIn = Intent(this,SignInActivity::class.java)
            startActivity(signUpToSignIn)
            finish()
        }
        usernameSignUp = findViewById(R.id.username_sign_up)
        emailSignUp = findViewById(R.id.email_sign_up)
        passwordSignUp = findViewById(R.id.password_sign_up)
        confirmPasswordSignUp = findViewById(R.id.confirm_password_sign_up)
        showPasswordSignUp = findViewById(R.id.show_password_sign_up)
        signUpButton = findViewById(R.id.sign_up_button)
        signUpButton.setOnClickListener {
            val signInIntent = Intent(this,SignInActivity::class.java)
            startActivity(signInIntent)
            finish()
        }

    }

    override fun onBackPressed() {
        val backToSignIn = Intent(this,SignInActivity::class.java)
        startActivity(backToSignIn)
        finish()
    }
}