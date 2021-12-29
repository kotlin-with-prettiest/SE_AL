package com.example.se_al.ui.classList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.se_al.R
import com.example.se_al.databinding.FragmentClassListBinding

class ClassListFragment : Fragment() {

    private lateinit var classListViewModel: ClassListViewModel
    private var _binding: FragmentClassListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        classListViewModel =
            ViewModelProvider(this).get(ClassListViewModel::class.java)

        _binding = FragmentClassListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.class1.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_class_list_to_classBaseFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}