package com.example.carhire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carhire.Adapters.CarInfoAdapter
import com.example.carhire.Models.CarInfoModel

class MainActivity : AppCompatActivity() {
    lateinit var carRecyclerView : RecyclerView
    lateinit var carArrayList: ArrayList<CarInfoModel>
    lateinit var type : Array<String>
    lateinit var price : Array<String>
    lateinit var image : Array<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carRecyclerView = findViewById(R.id.car_info_recyclerview)
        carRecyclerView.layoutManager = LinearLayoutManager(this)
        carRecyclerView.setHasFixedSize(true)

        type = arrayOf(
            "Volvo",
            "Mercedes Benz",
            "Toyota",
            "Honda"
        )
        price = arrayOf(
            "3,000",
            "4,000",
            "5,000",
            "6,000"
        )
        image = arrayOf(
            R.drawable.car,
            R.drawable.call,
            R.drawable.user,
            R.drawable.car,
        )
        carArrayList = arrayListOf<CarInfoModel>()
        getCarData()
    }

    private fun getCarData() {
        for (i in type.indices){
            val info = CarInfoModel(type[i],price[i],image[i])
            carArrayList.add(info)
        }
        carRecyclerView.adapter = CarInfoAdapter(carArrayList)
    }
}