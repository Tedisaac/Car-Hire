package com.example.carhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carhire.Adapters.CarInfoAdapter
import com.example.carhire.Models.CarInfoModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //arraylists
    lateinit var carArrayList: ArrayList<CarInfoModel>
    lateinit var type : Array<String>
    lateinit var price : Array<String>
    lateinit var image : Array<Int>
    //variable initialization
    lateinit var carRecyclerView : RecyclerView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView : NavigationView
    lateinit var navView : View
    lateinit var navViewProfileImage : CircleImageView
    lateinit var navViewProfileEmail : TextView
    lateinit var navViewProfileButton : TextView
    lateinit var menuDrawerImage : ImageView
    //firebase
    lateinit var auth: FirebaseAuth
    lateinit var database : FirebaseDatabase
    lateinit var reference : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        carRecyclerView = findViewById(R.id.car_info_recyclerview)
        carRecyclerView.layoutManager = LinearLayoutManager(this)
        carRecyclerView.setHasFixedSize(true)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        navView = navigationView.getHeaderView(0)
        navViewProfileImage = navView.findViewById(R.id.nav_profile_picture)
        navViewProfileEmail = navView.findViewById(R.id.nav_profile_email)
        navViewProfileButton = navView.findViewById(R.id.nav_profile_button)
        navHeaderProfile()
        getUserInfo()


        menuDrawerImage = findViewById(R.id.menu_drawer)
        menuDrawerImage.setOnClickListener {
            startNavView()
        }

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
            R.drawable.hyundai,
            R.drawable.hyundaileft,
            R.drawable.hyundairight,
        )
        carArrayList = arrayListOf<CarInfoModel>()
        getCarData()
    }

    private fun getUserInfo() {
        val user = auth.currentUser
        user?.let {
            val userEmail = user.email
            navViewProfileEmail.text = userEmail
        }
    }

    private fun navHeaderProfile() {
        navViewProfileButton.setOnClickListener {
            val navProfileIntent = Intent(this,ProfileActivity::class.java)
            startActivity(navProfileIntent)
            finish()
        }
    }


    private fun startNavView() {
        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)

        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    private fun getCarData() {
        for (i in type.indices){
            val info = CarInfoModel(type[i],price[i],image[i])
            carArrayList.add(info)
        }
        carRecyclerView.adapter = CarInfoAdapter(carArrayList)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.my_bookings -> {
                val mainToMyHiresIntent = Intent(this,MyHiresActivity::class.java)
                startActivity(mainToMyHiresIntent)
                finish()
            }
            R.id.logout -> {
                auth.currentUser?.let {
                    auth.signOut()
                }
                val logoutIntent = Intent(this, SignInActivity::class.java)
                startActivity(logoutIntent)
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}