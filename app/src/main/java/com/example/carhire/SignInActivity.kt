package com.example.carhire

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.carhire.Utils.SnackBarUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.concurrent.schedule

class SignInActivity : AppCompatActivity() {
    //variable initialization
    lateinit var emailTextSignIn : EditText
    lateinit var passwordSignIn : EditText
    lateinit var signInLayout : Button
    lateinit var signInBack : CircleImageView
    lateinit var createAccount : TextView
    lateinit var forgotPassword : TextView
    lateinit var showSignInPassword : CheckBox
    lateinit var signInRelativeLayout: RelativeLayout
    //firebase
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var  database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        signInRelativeLayout = findViewById(R.id.sign_in_relative_layout)
        signInLayout = findViewById(R.id.sign_in_button)
        signInLayout.setOnClickListener {
            signIn()
        }
        emailTextSignIn = findViewById(R.id.email_sign_in)
        passwordSignIn = findViewById(R.id.password_sign_in)
        signInBack = findViewById(R.id.sign_in_back)
        signInBack.setOnClickListener {
            finish()
        }
        createAccount = findViewById(R.id.create_account)
        forgotPassword = findViewById(R.id.forgot_password)
        createAccount.setOnClickListener {
            val createAccountIntent = Intent(this,SignUpActivity::class.java)
            startActivity(createAccountIntent)
            finish()
        }
        forgotPassword.setOnClickListener {
           val bottomSheetDialog = BottomSheetDialog(this,R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.bottom_sheet_fragment,
                findViewById<RelativeLayout>(R.id.bottm_sheet),
            )
            bottomSheetView.findViewById<Button>(R.id.forgot_password_button).setOnClickListener {
               /* val emailReset = bottomSheetView.findViewById<EditText>(R.id.email_forgot_password)
                if(emailReset.text.toString().isEmpty()){
                    emailReset.error = "Email Required"
                    emailReset.requestFocus()
                    return@setOnClickListener
                }
                auth.sendPasswordResetEmail(emailReset.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){


                        }
                    }*/
                showResetLinkDialog()
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        showSignInPassword = findViewById(R.id.show_password_sign_in)
        showSignInPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                passwordSignIn.transformationMethod = null
            } else{
                passwordSignIn.transformationMethod = PasswordTransformationMethod()
            }
        }
    }

    private fun signIn() {
        if (emailTextSignIn.text.toString().isEmpty()){
            emailTextSignIn.error = "Email required"
            emailTextSignIn.requestFocus()
            return
        }
        if (passwordSignIn.text.toString().isEmpty()){
            passwordSignIn.error = "Password required"
            passwordSignIn.requestFocus()
            return
        }
       /* user?.let {
            val emailVerified = user.isEmailVerified
            if (!emailVerified){
                signInEvent()
            } else {
                SnackBarUtil().showSnackBar(this,"Please Verify Email",signInRelativeLayout)
            }
        }*/
        signInEvent()


    }

    private fun signInEvent() {
        auth.signInWithEmailAndPassword(emailTextSignIn.text.toString(),passwordSignIn.text.toString())
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    SnackBarUtil().showSnackBar(this,"Sign in successful",signInRelativeLayout)
                    Timer().schedule(3000){
                        pageChooser()
                        //toInformationPage()
                        //toMainPage()
                    }

                } else {
                    Log.e(TAG, "signIn: " + task.exception )
                }
            }
    }

    private fun pageChooser() {
        val uid = auth.uid
        (if (uid != null) {
            reference.child(uid).child("phone").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        toMainPage()
                    } else {
                        toInformationPage()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        })
    }

    private fun toInformationPage() {
        val infoIntent = Intent(this,PersonalInfoActivity::class.java)
        startActivity(infoIntent)
        finish()
    }

    private fun toMainPage() {
        val signInIntent  = Intent(this,MainActivity::class.java)
        startActivity(signInIntent)
        finish()
    }

    private fun showResetLinkDialog() {
        val resetLinkDialog : AlertDialog = MaterialAlertDialogBuilder(this,R.style.RoundedMaterialDialog)
            .setView(R.layout.password_reset_dialog)
            .setCancelable(false)
            .show()
        resetLinkDialog.findViewById<ImageView>(R.id.reset_link_close)?.setOnClickListener {
            resetLinkDialog.dismiss()
        }
    }
}