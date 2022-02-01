package com.example.carhire

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.carhire.Utils.SnackBarUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.File
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
    lateinit var imageUri : Uri
    lateinit var bitmap: Bitmap
    //firebase
    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var storageReference: StorageReference
    //activity result launcher
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")


        userEmailText = findViewById(R.id.user_email_text)
        userNameText = findViewById(R.id.user_name_text)
        userNumberText = findViewById(R.id.user_number_text)
        userGenderText = findViewById(R.id.user_gender_text)
        userAgeText = findViewById(R.id.user_age_text)
        profilePageImage = findViewById(R.id.profile_page_image)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                handleCameraImage(result.data)
            }

        }
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted){
                    moveToCamera()
                } else {
                    SnackBarUtil().showSnackBar(this,"Please Grant Required Permissions",profileLayout)
                }
            }

        }
        profilePageImage.setOnClickListener {
            requestPermissionLauncher.launch(arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ))
            //moveToCamera()
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
        setUserProfileImage()
    }

    private fun setUserProfileImage() {
        storageReference = FirebaseStorage.getInstance().getReference("users/${auth.uid}")
        val localFile = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val imageBitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            profilePageImage.setImageBitmap(imageBitmap)
        }
    }

    private fun moveToCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(cameraIntent)
    }

    private fun handleCameraImage(data: Intent?) {
        bitmap = data?.extras?.get("data") as Bitmap

        uploadImageToDB()


    }

    private fun uploadImageToDB() {
        bitmap?.apply {
            profilePageImage.setImageBitmap(bitmap)
            val imageString = toBase64String()
        }
        val path = MediaStore.Images.Media.insertImage(applicationContext.contentResolver,bitmap,"val", null)
        imageUri = Uri.parse("$path")
        Log.e(TAG, "uploadImageToDB: $imageUri", )
        storageReference = FirebaseStorage.getInstance().getReference("users/${auth.uid}")
        storageReference.putFile(imageUri).addOnSuccessListener {
            Log.e(TAG, "uploadImageToDB: Picture saved", )
        }.addOnFailureListener { exception ->
            Log.e(TAG, "uploadImageToDB: ${exception.toString()}", )
        }
    }

    private fun Bitmap.toBase64String():String{
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.JPEG,10,this)
            return Base64.encodeToString(toByteArray(),Base64.DEFAULT)
        }
    }

    private fun setUserProfileData() {
        val user = auth.currentUser
        val userId = auth.uid
        user?.let {
            val userName = user.displayName
            userNameText.text = userName
            val userEmail = user.email
            userEmailText.text = userEmail
        }
        if (userId != null) {
            reference.child(userId).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val phoneNumber = snapshot.child("phone").value
                        val gender = snapshot.child("gender").value
                        val age = snapshot.child("age").value
                        userNumberText.text = phoneNumber as CharSequence?
                        userGenderText.text = gender as CharSequence?
                        userAgeText.text = age as CharSequence?
                    } else{
                        Log.e(TAG, "onDataChange: Data does not exist", )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: ${error.message}", )
                }

            })

        }

    }

    override fun onBackPressed() {
        val profileToMainIntent = Intent(this,MainActivity::class.java)
        startActivity(profileToMainIntent)
        finish()
    }
}