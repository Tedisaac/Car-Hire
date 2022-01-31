package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import de.hdodenhof.circleimageview.CircleImageView

class CarDetailsActivity : AppCompatActivity() {
    lateinit var carDetailsBack : CircleImageView
    lateinit var carImageSlider : ImageSlider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        carDetailsBack = findViewById(R.id.car_details_back)
        carDetailsBack.setOnClickListener {
            carDetailsBackFun()
        }
        carImageSlider = findViewById(R.id.car_image_slider)
        val carImageList = ArrayList<SlideModel>()
        carImageList.add(SlideModel(R.drawable.hyundai,"Ksh.2500"))
        carImageList.add(SlideModel(R.drawable.hyundaileft))
        carImageList.add(SlideModel(R.drawable.hyundairight))
        carImageSlider.setImageList(carImageList,ScaleTypes.CENTER_CROP)
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