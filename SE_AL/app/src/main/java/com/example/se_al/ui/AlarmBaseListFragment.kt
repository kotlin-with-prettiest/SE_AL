package com.example.se_al.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.se_al.SampleData
import com.example.se_al.adapter.AlarmBaseListAdapter
import com.example.se_al.databinding.FragmentAlarmBaseListBinding

class AlarmBaseListFragment  : Fragment() {

    private var _binding: FragmentAlarmBaseListBinding? = null

    private val binding get() = _binding!!

    private val viewModel : AlarmBaseListViewModel by activityViewModels()

        var sampleData = arrayListOf<SampleData>(
        SampleData("sample1","2021-12-29","title1","memo1"),
        SampleData("sample2","2021-12-30","title2","memo2"),
        SampleData("sample3","2021-12-31","title3","memo3")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val mAdapter = AlarmBaseListAdapter(this,sampleData)
//        binding.recyclerAlarmItem.adapter =mAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlarmBaseListBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.recyclerAlarmItem.setOnClickListener{
//            findNavController().navigate(R.id.action_alarmBaseListFragment_to_alarmFixFragment)
//        }


        return root
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