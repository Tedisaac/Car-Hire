package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carhire.Adapters.MyHiresAdapter
import com.example.carhire.Models.MyHiresInfoModel
import de.hdodenhof.circleimageview.CircleImageView

class MyHiresActivity : AppCompatActivity() {
    //arraylists
    lateinit var myHiresArrayList: ArrayList<MyHiresInfoModel>
    lateinit var model : Array<String>
    lateinit var date : Array<String>
    lateinit var price : Array<String>
    lateinit var plate : Array<String>
    lateinit var owner : Array<String>
    lateinit var contact : Array<String>
    //variable initialization
    lateinit var hiresRecyclerView: RecyclerView
    lateinit var myHiresBack : CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_hires)

        myHiresBack = findViewById(R.id.my_hires_back)
        myHiresBack.setOnClickListener {
            val myHiresToMain = Intent(this,MainActivity::class.java)
            startActivity(myHiresToMain)
            finish()
        }
        hiresRecyclerView = findViewById(R.id.my_hires_recycler_view)
        hiresRecyclerView.layoutManager = LinearLayoutManager(this)
        hiresRecyclerView.setHasFixedSize(true)
        model = arrayOf(
            "volvo",
            "Toyota Prius",
            "Toyota Premio",
            "Jeep"
        )
        date = arrayOf(
            "volvo",
            "Toyota Prius",
            "Toyota Premio",
            "Jeep"
        )
        price = arrayOf(
            "2,000",
            "3,000",
            "4,000",
            "7,000"
        )
        plate = arrayOf(
            "KAA 123E",
            "KDE 124E",
            "KHT 847T",
            "KDB 097G"
        )
        owner = arrayOf(
            "Mark",
            "Charles",
            "Ben",
            "Purity"
        )
        contact = arrayOf(
            "0987654333",
            "0104151629",
            "9583058853",
            "4959049490"
        )
        myHiresArrayList = arrayListOf<MyHiresInfoModel>()
        displayMyHiresData()
    }

    private fun displayMyHiresData() {
        for (i in model.indices){
            val hireInfo = MyHiresInfoModel(model[i],date[i],price[i],plate[i],owner[i],contact[i])
            myHiresArrayList.add(hireInfo)
        }
        hiresRecyclerView.adapter = MyHiresAdapter(myHiresArrayList)
    }

    override fun onBackPressed() {
        val myHiresToMainIntent = Intent(this,MainActivity::class.java)
        startActivity(myHiresToMainIntent)
        finish()
    }
}