package com.example.seal.viewpagerfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.seal.R
import com.example.seal.adapter.ActivityFragmentStateAdapter
import com.example.seal.databinding.FragmentViewpagerContainerBinding
import com.example.seal.ui.main.BaseDataBindingFragment
import com.example.seal.viewModel.ViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerContainerFragment : BaseDataBindingFragment<FragmentViewpagerContainerBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_viewpager_container

    val appbarViewModel by activityViewModels<ViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewPager2
        val viewPager = dataBinding!!.viewPager

        /*
               🔥 Set Adapter for ViewPager inside this fragment using this Fragment,
               more specifically childFragmentManager as param

               https://stackoverflow.com/questions/61779776/leak-canary-detects-memory-leaks-for-tablayout-with-viewpager2
         */
        viewPager.adapter =
            ActivityFragmentStateAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

        // TabLayout
        val tabLayout = dataBinding!!.tabLayout

        // Bind tabs and viewpager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Class"
                1 -> tab.text = "Home"
                2 -> tab.text = "Setting"
            }
        }.attach()


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                appbarViewModel.currentNavController.value =
                    appbarViewModel.currentNavController.value
            }
        })

    }

    override fun onDestroyView() {

        val viewPager2 = dataBinding?.viewPager

        /*
            Without setting ViewPager2 Adapter it causes memory leak

            https://stackoverflow.com/questions/62851425/viewpager2-inside-a-fragment-leaks-after-replacing-the-fragment-its-in-by-navig
         */
        viewPager2?.let {
            it.adapter = null
        }

        super.onDestroyView()
    }

}