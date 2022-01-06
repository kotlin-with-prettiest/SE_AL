package com.example.se_al

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.se_al.databinding.ActivityMainBinding
import com.example.se_al.ui.home.CcFragment
import com.example.se_al.ui.home.HhFragment
import com.example.se_al.ui.home.HomeDashboardFragment
import com.example.se_al.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var ft = supportFragmentManager

    lateinit var calendar: HomeFragment
    lateinit var dashboard: HomeDashboardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val btn: Button = findViewById(R.id.btn_screen_change_to_dashboard)
//        btn.setOnClickListener {
//            val intent = Intent(this, DashboardActivity::class.java)
//            startActivity(intent)
//
//        }
//        android:id="@+id/nav_host_fragment_activity_main"




        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_class_list, R.id.navigation_home_calendar, R.id.navigation_setting))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }


}

