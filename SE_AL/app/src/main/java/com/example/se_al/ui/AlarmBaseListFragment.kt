package com.example.se_al.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.se_al.R
import com.example.se_al.databinding.FragmentAlarmBaseListBinding

class AlarmBaseListFragment  : Fragment() {

    private var _binding: FragmentAlarmBaseListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlarmBaseListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerAlarmItem.setOnClickListener{
            findNavController().navigate(R.id.action_alarmBaseListFragment_to_alarmFixFragment)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}