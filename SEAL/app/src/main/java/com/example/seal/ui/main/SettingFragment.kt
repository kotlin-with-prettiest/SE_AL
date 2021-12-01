package com.example.seal.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seal.MainActivity
import com.example.seal.R
import com.example.seal.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.alarmTime.setOnClickListener{
            val mActivity = activity as MainActivity
            mActivity.gotoAlarmTimeFragment(SettingAlarmTimeFragment())
        }

        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    companion object {

    }
}