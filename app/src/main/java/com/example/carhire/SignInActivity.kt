package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.hdodenhof.circleimageview.CircleImageView

class SignInActivity : AppCompatActivity() {
    lateinit var emailTextSignIn : EditText
    lateinit var passwordSignIn : EditText
    lateinit var signInLayout : Button
    lateinit var signInBack : CircleImageView
    lateinit var createAccount : TextView
    lateinit var forgotPassword : TextView
    lateinit var showSignInPassword : CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInLayout = findViewById(R.id.sign_in_button)
        signInLayout.setOnClickListener {
            val signInIntent  = Intent(this,MainActivity::class.java)
            startActivity(signInIntent)
            finish()
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
                bottomSheetDialog.dismiss()
                showResetLinkDialog()
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        showSignInPassword = findViewById(R.id.show_password_sign_in)
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