package com.example.a6_6.blankfragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.a6_6.R
import com.example.a6_6.databinding.FragmentHome1Binding

class HomeFragment1 : BaseDataBindingFragment<FragmentHome1Binding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_home1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding!!.btnNextPage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment1_to_homeFragment2)
        }

    }

}
