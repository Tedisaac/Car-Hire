package com.example.carhire

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.carhire.Utils.SnackBarUtil
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.Year
import java.util.*
import kotlin.concurrent.schedule
import kotlin.properties.Delegates

class PersonalInfoActivity : AppCompatActivity() {
    //variable initialization
    lateinit var phoneNumber : EditText
    lateinit var calendarView: CalendarView
    lateinit var gender : AutoCompleteTextView
    lateinit var genderInputLayout : TextInputLayout
    lateinit var continueButton : Button
    lateinit var personalInfoRelativeLayout: RelativeLayout
    lateinit var birthDate : String
    lateinit var birthYear : String
    lateinit var birthMonth : String
    lateinit var birthDay : String
    var age by Delegates.notNull<Int>()

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        personalInfoRelativeLayout = findViewById(R.id.personal_info_relative_layout)
        phoneNumber = findViewById(R.id.phone_number_edittext)
        calendarView =findViewById(R.id.calender)
        calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
            //val currentYear = Calendar.YEAR
            age = 2022 - i
            birthYear = i.toString()
            birthMonth = i2.toString()
            birthDay = i3.toString()
            birthDate = "$birthDay/$birthMonth/$birthYear"
            
        }
        gender = findViewById(R.id.gender_auto_complete_textview)
        genderInputLayout = findViewById(R.id.gender_text_input_layout)
        var genders = resources.getStringArray(R.array.gen)
        var arrayAdapter = ArrayAdapter(this,R.layout.list_item,genders)
        gender.setAdapter(arrayAdapter)

        continueButton = findViewById(R.id.continue_button)
        continueButton.setOnClickListener {
            sendProfileInfoToDB()
        }
    }

    private fun sendProfileInfoToDB() {
        if (phoneNumber.text.toString().isEmpty()){
            phoneNumber.error = "Phone Number Required"
            phoneNumber.requestFocus()
            return
        }
        
        val userUid = auth.uid
        if (userUid != null) {
            reference.child(userUid).child("phone").setValue(phoneNumber.text.toString())
            reference.child(userUid).child("gender").setValue(gender.text.toString())
            reference.child(userUid).child("birthdate").setValue(birthDate)
            reference.child(userUid).child("age").setValue(age.toString())
            SnackBarUtil().showSnackBar(this,"Information Saved",personalInfoRelativeLayout)
        }
        Timer().schedule(3000){
            toMainPage()
        }



    }

    private fun toMainPage() {
        val mainPageIntent = Intent(this,MainActivity::class.java)
        startActivity(mainPageIntent)
        finish()
    }
}