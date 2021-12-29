package com.example.seal.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seal.databinding.FragmentSettingBinding
import androidx.navigation.fragment.findNavController


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.settingFragment = this
    }

    fun goSettingAlarmTime(){
        val action = SettingFragmentDirections.actionSettingFragmentToSettingAlarmTimeFragment()
        findNavController().navigate(action)
    }

    fun goPersonalInfo(){
        val action = SettingFragmentDirections.actionSettingFragmentToSettingPersonalInfoFragment()
        findNavController().navigate(action)
    }
}