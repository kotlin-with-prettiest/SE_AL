package com.example.se_al.ui.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.se_al.R
import com.example.se_al.databinding.FragmentHhBinding

class HhFragment : Fragment() {


    private var _binding: FragmentHhBinding? = null

    private val binding get() = _binding!!

    var flag: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentHhBinding.inflate(inflater, container, false)
        val root: View = binding.root



        if (flag < 0) {
            Log.d("first flag", flag.toString())
            flag = 0
            childFragmentManager.beginTransaction()
                .replace(R.id.hh, CcFragment()).commit()
        }


        binding.btnScreenChange.setOnClickListener {
            Log.d("click flag", flag.toString())

            if (flag == 0) {
                flag += 1
                childFragmentManager.beginTransaction()
                    .replace(R.id.hh, DdFragment()).commit()
                binding.btnFloatUp.visibility= View.VISIBLE
            } else {
                flag -= 1
                childFragmentManager.beginTransaction()
                    .replace(R.id.hh, CcFragment()).commit()
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