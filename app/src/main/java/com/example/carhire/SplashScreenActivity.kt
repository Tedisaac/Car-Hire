package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {
    lateinit var carAnime : LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        carAnime = findViewById(R.id.car_animation)
        carAnime.playAnimation()
        Timer().schedule(3000){
            moveToMainScreen()
        }


    }

    private fun moveToMainScreen() {
        val mainIntent = Intent(this,SignInActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}