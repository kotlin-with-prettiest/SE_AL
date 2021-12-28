package com.example.se_al.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.se_al.R
import com.example.se_al.databinding.ActivityMainBinding
import com.example.se_al.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        
        //화면전환 해야함
//        binding.btnScreenChange.setOnClickListener {
//            
//            val navView : BottomNavigationView = ActivityMainBinding.inflate(layoutInflater).navView
//            val navController = findNavController(R.id.nav_host_fragment_activity_main)
//
//            val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_class_list, R.id.navigation_home_dashboard, R.id.navigation_setting))
//            setupActionBarWithNavController(navController, appBarConfiguration)
//            navView.setupWithNavController(navController)
//
//
//        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}