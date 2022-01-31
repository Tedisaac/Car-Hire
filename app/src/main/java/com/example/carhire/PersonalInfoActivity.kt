package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.textfield.TextInputLayout

class PersonalInfoActivity : AppCompatActivity() {
    //variable initialization
    lateinit var phoneNumber : EditText
    lateinit var calendarView: CalendarView
    lateinit var gender : AutoCompleteTextView
    lateinit var genderInputLayout : TextInputLayout
    lateinit var continueButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        phoneNumber = findViewById(R.id.phone_number_edittext)
        calendarView =findViewById(R.id.calender)
        gender = findViewById(R.id.gender_auto_complete_textview)
        genderInputLayout = findViewById(R.id.gender_text_input_layout)
        var genders = resources.getStringArray(R.array.gen)
        var arrayAdapter = ArrayAdapter(this,R.layout.list_item,genders)
        gender.setAdapter(arrayAdapter)
        gender.setOnItemClickListener { _, _, _, _ ->

        }
        continueButton = findViewById(R.id.continue_button)
        continueButton.setOnClickListener {
            toMainPage()
        }
    }

    private fun toMainPage() {
        val mainPageIntent = Intent(this,MainActivity::class.java)
        startActivity(mainPageIntent)
        finish()
    }
}