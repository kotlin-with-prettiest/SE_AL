package com.example.se_al.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.se_al.MainActivity
import com.example.se_al.R
import com.example.se_al.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        

        screenMaintain()

        binding.btnScreenChange.setOnClickListener {
            screenChange()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun screenChange(){
        Log.d("before flag", MainActivity.flag.home_flag.toString())
        if (MainActivity.flag.home_flag == 0) {
            MainActivity.flag.home_flag = 1
            childFragmentManager.beginTransaction()
                .replace(R.id.home_layout, HomeDashboardFragment()).commit()
            binding.btnFloatUp.visibility= View.VISIBLE
        } else {
            MainActivity.flag.home_flag = 0
            childFragmentManager.beginTransaction()
                .replace(R.id.home_layout, HomeCalendarFragment()).commit()
            binding.btnFloatUp.visibility= View.INVISIBLE
        }
        Log.d("after flag", MainActivity.flag.home_flag.toString())
    }


    fun screenMaintain(){
        Log.d("before maintain flag", MainActivity.flag.home_flag.toString())
        if (MainActivity.flag.home_flag == 0) {
            childFragmentManager.beginTransaction()
                .replace(R.id.home_layout, HomeCalendarFragment()).commit()
            binding.btnFloatUp.visibility= View.INVISIBLE
        } else {
            childFragmentManager.beginTransaction()
                .replace(R.id.home_layout, HomeDashboardFragment()).commit()
            binding.btnFloatUp.visibility= View.VISIBLE
        }
        Log.d("after maintain flag", MainActivity.flag.home_flag.toString())
    }
}