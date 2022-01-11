package com.example.se_al.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.se_al.R
import com.example.se_al.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    var flag: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        if (flag < 0) {
            Log.d("first flag", flag.toString())
            flag = 0
            childFragmentManager.beginTransaction()
                .replace(R.id.home_layout, HomeCalendarFragment()).commit()
        }


        binding.btnScreenChange.setOnClickListener {
            Log.d("click flag", flag.toString())

            if (flag == 0) {
                flag += 1
                childFragmentManager.beginTransaction()
                    .replace(R.id.home_layout, HomeDashboardFragment()).commit()
                binding.btnFloatUp.visibility= View.VISIBLE
            } else {
                flag -= 1
                childFragmentManager.beginTransaction()
                    .replace(R.id.home_layout, HomeCalendarFragment()).commit()
                binding.btnFloatUp.visibility= View.INVISIBLE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}