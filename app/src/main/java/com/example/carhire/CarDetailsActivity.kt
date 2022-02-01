package com.example.carhire

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import de.hdodenhof.circleimageview.CircleImageView

class CarDetailsActivity : AppCompatActivity() {
    lateinit var carDetailsBack : CircleImageView
    lateinit var carImageSlider : ImageSlider
    lateinit var callOwner : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        val requestCallPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
            if (isGranted){
                makeCall()
            } else {

            }

        }

        carDetailsBack = findViewById(R.id.car_details_back)
        carDetailsBack.setOnClickListener {
            carDetailsBackFun()
        }
        carImageSlider = findViewById(R.id.car_image_slider)
        val carImageList = ArrayList<SlideModel>()
        carImageList.add(SlideModel(R.drawable.hyundai,"Ksh.2500"))
        carImageList.add(SlideModel(R.drawable.hyundaileft,"Ksh.2500"))
        carImageList.add(SlideModel(R.drawable.hyundairight,"Ksh.2500"))
        carImageSlider.setImageList(carImageList,ScaleTypes.CENTER_CROP)

        callOwner = findViewById(R.id.call_owner)
        callOwner.setOnClickListener {
            requestCallPermission.launch(android.Manifest.permission.CALL_PHONE)
        }
    }

    private fun makeCall() {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:"+"0797877878")
        startActivity(callIntent)
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