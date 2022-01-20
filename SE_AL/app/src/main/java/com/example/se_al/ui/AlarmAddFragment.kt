package com.example.se_al.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.se_al.databinding.FragmentAlarmAddBinding

class AlarmAddFragment  : Fragment() {

    private var _binding: FragmentAlarmAddBinding? = null

    private val binding get() = _binding!!

    //keyboard inputmanager
    var imm : InputMethodManager?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlarmAddBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}