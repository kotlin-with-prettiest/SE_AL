package com.example.a6_6.blankfragment


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.a6_6.R
import com.example.a6_6.databinding.FragmentNotification2Binding


class NotificationFragment2 : BaseDataBindingFragment<FragmentNotification2Binding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_notification2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding!!.btnNextPage.setOnClickListener {
            findNavController().navigate(R.id.action_notificationFragment2_to_notificationFragment3)
        }
    }

}