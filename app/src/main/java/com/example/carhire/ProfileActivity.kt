package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.carhire.Utils.SnackBarUtil
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import java.util.jar.Manifest

class ProfileActivity : AppCompatActivity() {
    //variable initialization
    lateinit var saveInfo : TextView
    lateinit var profileLayout : RelativeLayout
    lateinit var profilePageBack : CircleImageView
    lateinit var profilePageImage : CircleImageView
    lateinit var userEmailText : TextView
    lateinit var userNameText : TextView
    lateinit var userNumberText : TextView
    lateinit var userGenderText : TextView
    lateinit var userAgeText : TextView
    //firebase
    lateinit var auth : FirebaseAuth
    //activity result launchers
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        if (isGranted){

        }else{

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        auth = FirebaseAuth.getInstance()


        userEmailText = findViewById(R.id.user_email_text)
        userNameText = findViewById(R.id.user_name_text)
        userNumberText = findViewById(R.id.user_number_text)
        userGenderText = findViewById(R.id.user_gender_text)
        userAgeText = findViewById(R.id.user_age_text)
        profilePageImage = findViewById(R.id.profile_page_image)
        profilePageImage.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
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
        setUserProfileData()
    }

    private fun setUserProfileData() {
        val user = auth.currentUser
        user?.let {
            val userName = user.displayName
            userNameText.text = userName
            val userEmail = user.email
            userEmailText.text = userEmail


        }
    }

    override fun onBackPressed() {
        val profileToMainIntent = Intent(this,MainActivity::class.java)
        startActivity(profileToMainIntent)
        finish()
    }
}