package com.example.se_al.ui.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.se_al.MainActivity
import com.example.se_al.databinding.FragmentSettingPersonalInfoBinding
import com.example.se_al.login.LoginActivity

class SettingPersonalInfoFragment : Fragment() {

    private var _binding: FragmentSettingPersonalInfoBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingPersonalInfoBinding.inflate(inflater, container, false)

        val root: View = binding.root

        //로그아웃 버튼
        binding.btnLogout.setOnClickListener {
            Log.d("Setting", "logout")
            val intent = Intent(this@SettingPersonalInfoFragment.context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}