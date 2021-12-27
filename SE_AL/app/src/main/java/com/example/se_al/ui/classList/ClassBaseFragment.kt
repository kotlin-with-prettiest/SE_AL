package com.example.se_al.ui.ClassBase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.se_al.R
import com.example.se_al.databinding.FragmentClassBaseBinding

class ClassBaseFragment : Fragment() {

    private var _binding: FragmentClassBaseBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClassBaseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_classBaseFragment_to_addAlarmFragment)
        }

        binding.btnSetting.setOnClickListener {
            findNavController().navigate(R.id.action_classBaseFragment_to_classBaseAlarmSettingFragment)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}