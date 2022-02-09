package com.example.se_al.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.se_al.R
import com.example.se_al.data.UserDatabase
import com.example.se_al.databinding.FragmentClassBaseBinding
import com.example.se_al.databinding.FragmentClassListBinding
import com.example.se_al.databinding.FragmentSettingBinding
import com.example.se_al.ui.classList.ClassListViewModel

class SettingFragment : Fragment() {
    private lateinit var settingViewModel: SettingViewModel
    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.alarmTime.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_setting_to_settingAlarmTimeFragment)
        }

        binding.personalInfoModify.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_setting_to_settingPersonalInfoFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}