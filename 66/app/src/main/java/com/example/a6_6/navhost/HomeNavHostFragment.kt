package com.example.a6_6.navhost

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.a6_6.R
import com.example.a6_6.blankfragment.BaseDataBindingFragment
import com.example.a6_6.databinding.FragmentNavhostHomeBinding
import com.example.a6_6.viewmodel.AppbarViewModel


class HomeNavHostFragment : BaseDataBindingFragment<FragmentNavhostHomeBinding>() {
    override fun getLayoutRes(): Int = R.layout.fragment_navhost_home

    private val appbarViewModel by activityViewModels<AppbarViewModel>()

    private var navController: NavController? = null

    private val nestedNavHostFragmentId = R.id.nestedHomeNavHostFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

        // Listen on back press
//        listenOnBackPressed()

    }

//    private fun listenOnBackPressed() {
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
//    }


    override fun onResume() {
        super.onResume()
//        callback.isEnabled = true

        // Set this navController as ViewModel's navController
        appbarViewModel.currentNavController.value = navController
    }

    override fun onPause() {
        super.onPause()
//        callback.isEnabled = false
    }

    /**
     * This callback should be created with Disabled because on rotation ViewPager creates
     * NavHost fragments that are not on screen, destroys them afterwards but it might take
     * up to 5 seconds.
     *
     * ### Note: During that interval touching back button sometimes call incorrect [OnBackPressedCallback.handleOnBackPressed] instead of this one if callback is **ENABLED**
     */
//    val callback = object : OnBackPressedCallback(false) {
//
//        override fun handleOnBackPressed() {
//
//            // Check if it's the root of nested fragments in this navhost
//            if (navController?.currentDestination?.id == navController?.graph?.startDestination) {
//
//                Toast.makeText(requireContext(), "🏠 AT START DESTINATION ", Toast.LENGTH_SHORT)
//                    .show()
//
//                /*
//                    Disable this callback because calls OnBackPressedDispatcher
//                     gets invoked  calls this callback  gets stuck in a loop
//                 */
//                isEnabled = false
//                requireActivity().onBackPressed()
//                isEnabled = true
//
//            } else {
//                navController?.navigateUp()
//            }
//
//        }
//    }

}