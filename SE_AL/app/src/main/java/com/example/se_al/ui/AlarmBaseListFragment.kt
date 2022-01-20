package com.example.se_al.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.se_al.databinding.FragmentAlarmBaseListBinding

class AlarmBaseListFragment  : Fragment() {

    private var _binding: FragmentAlarmBaseListBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel : AlarmBaseListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlarmBaseListBinding.inflate(inflater, container, false)

        activity?.let {
            viewModel = ViewModelProvider(it).get(AlarmBaseListViewModel::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}