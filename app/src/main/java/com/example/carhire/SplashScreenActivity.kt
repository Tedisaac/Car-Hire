package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var carAnime : LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        auth = FirebaseAuth.getInstance()
        carAnime = findViewById(R.id.car_animation)
        carAnime.playAnimation()
        screenChoice()



    }

    private fun screenChoice() {
        val userExist = auth.currentUser
        if (userExist!=null){
            Timer().schedule(3000){
                moveToMainScreen()
            }
        } else {
            Timer().schedule(3000){
                moveToSignInScreen()
            }

        }
    }

    private fun moveToSignInScreen() {
        val mainIntent = Intent(this,SignInActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun moveToMainScreen() {
        val mainIntent = Intent(this,MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}