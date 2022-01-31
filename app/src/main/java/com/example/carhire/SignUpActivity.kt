package com.example.carhire

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RelativeLayout
import com.example.carhire.Models.UserModel
import com.example.carhire.Utils.SnackBarUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.concurrent.schedule

class SignUpActivity : AppCompatActivity() {
    //Variable Initialization
    lateinit var usernameSignUp : EditText
    lateinit var emailSignUp : EditText
    lateinit var passwordSignUp : EditText
    lateinit var confirmPasswordSignUp : EditText
    lateinit var showPasswordSignUp : CheckBox
    lateinit var signUpButton : Button
    lateinit var signUpBack : CircleImageView
    lateinit var signUpLayout: RelativeLayout
    //Firebase
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val user = auth.currentUser
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        signUpLayout = findViewById(R.id.sign_up_relative_layout)
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
        showPasswordSignUp.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                passwordSignUp.transformationMethod = null
                confirmPasswordSignUp.transformationMethod = null
            } else{
                passwordSignUp.transformationMethod = PasswordTransformationMethod()
                confirmPasswordSignUp.transformationMethod = PasswordTransformationMethod()

            }
        }
        signUpButton = findViewById(R.id.sign_up_button)
        signUpButton.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {
        if (usernameSignUp.text.toString().isEmpty()){
            usernameSignUp.error = "Username required"
            usernameSignUp.requestFocus()
            return
        }
        if (emailSignUp.text.toString().isEmpty()){
            emailSignUp.error = "Email required"
            emailSignUp.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailSignUp.text.toString()).matches()){
            emailSignUp.error = "Invalid Email Address"
            emailSignUp.requestFocus()
            return
        }
        if (passwordSignUp.text.toString().isEmpty()){
            passwordSignUp.error = "Password required"
            passwordSignUp.requestFocus()
            return
        }
        if (confirmPasswordSignUp.text.toString().isEmpty()){
            confirmPasswordSignUp.error = "Confirm password required"
            confirmPasswordSignUp.requestFocus()
            return
        }
        if (passwordSignUp.text.toString() != confirmPasswordSignUp.text.toString()){
            confirmPasswordSignUp.error = "Passwords don't match"
            confirmPasswordSignUp.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(emailSignUp.text.toString(),passwordSignUp.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    SnackBarUtil().showSnackBar(this,"Account Registered Successfully",signUpLayout)
                    sendDataToDB()
                    saveUserProfile()
                    sendVerificationEmail()
                    Timer().schedule(3000){
                        backToSignIn()
                    }

                } else{
                    Log.e(TAG, "signUp: " + task.exception)
                }
            }


    }

    private fun sendVerificationEmail() {
        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.e(TAG, "verification Email Sent")
                }
            }
    }

    private fun saveUserProfile() {
        val userProfile = userProfileChangeRequest {
            displayName = usernameSignUp.text.toString()


        }
        user!!.updateProfile(userProfile)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Log.e(TAG, "Profile Updated" )
                }
            }

        user!!.updateEmail(emailSignUp.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.e(TAG, "Email Updated " )
                }
            }
    }

    private fun sendDataToDB() {
        var model = UserModel(usernameSignUp.text.toString(),emailSignUp.text.toString())
        var uid = reference.push().key
        reference.child(uid!!).setValue(model)
    }

    private fun backToSignIn() {
        val signInIntent = Intent(this,SignInActivity::class.java)
        startActivity(signInIntent)
        finish()
    }

    override fun onBackPressed() {
        val backToSignIn = Intent(this,SignInActivity::class.java)
        startActivity(backToSignIn)
        finish()
    }
}