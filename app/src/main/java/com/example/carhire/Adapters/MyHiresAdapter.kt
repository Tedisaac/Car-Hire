package com.example.carhire.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.carhire.Models.MyHiresInfoModel
import com.example.carhire.R

class MyHiresAdapter(private val hiresInfo : ArrayList<MyHiresInfoModel>) :
    RecyclerView.Adapter<MyHiresAdapter.HiresViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_hires_info,parent,false)
        return HiresViewHolder(view)
    }

    override fun onBindViewHolder(holder: HiresViewHolder, position: Int) {
        val currCar = hiresInfo[position]
        holder.model.text = currCar.model
        holder.date.text = currCar.date
        holder.price.text = currCar.price
        holder.plate.text = currCar.plate
        holder.owner.text = currCar.owner
        holder.contact.text = currCar.contact
    }

    override fun getItemCount(): Int {
        return hiresInfo.size
    }

    class HiresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val model : TextView = itemView.findViewById(R.id.my_hires_car_model)
        val date : TextView = itemView.findViewById(R.id.my_hires_date_textview)
        val price : TextView = itemView.findViewById(R.id.my_hires_price_textview)
        val plate : TextView = itemView.findViewById(R.id.my_hires_no_plate_textview)
        val owner : TextView = itemView.findViewById(R.id.my_hires_owner_textview)
        val contact : TextView = itemView.findViewById(R.id.my_hires_number_textview)

    }
}