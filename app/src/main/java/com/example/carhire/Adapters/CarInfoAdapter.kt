package com.example.carhire.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.carhire.CarDetailsActivity
import com.example.carhire.Models.CarInfoModel
import com.example.carhire.R

class CarInfoAdapter(private val carInfo : ArrayList<CarInfoModel>) : RecyclerView.Adapter<CarInfoAdapter.CarViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_info,
        parent,false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val currentCar = carInfo[position]
        holder.carType.text = currentCar.carType
        holder.carPrice.text = currentCar.carPrice
        holder.carImage.setImageResource(currentCar.carImage)
        holder.rightArrow.playAnimation()
        holder.rightArrow.setOnClickListener {
            val activity = holder.itemView.context as Activity
            val carDetailsIntent = Intent(activity,CarDetailsActivity::class.java)
            activity.startActivity(carDetailsIntent)
            activity.finish()
        }
    }

    override fun getItemCount(): Int {
        return carInfo.size
    }
    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val carType : TextView = itemView.findViewById(R.id.vehicle_type)
        val carPrice : TextView = itemView.findViewById(R.id.vehicle_price)
        val carImage : ImageView = itemView.findViewById(R.id.vehicle_image)
        val rightArrow : LottieAnimationView = itemView.findViewById(R.id.more_vehicle_info)
    }

}