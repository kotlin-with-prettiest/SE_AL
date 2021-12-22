package com.example.seal.navhost


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.seal.R
import com.example.seal.databinding.FragmentNavhostParentBinding
import com.example.seal.ui.main.BaseDataBindingFragment
import com.example.seal.viewModel.ViewModel


class ParentNavHostFragment : BaseDataBindingFragment<FragmentNavhostParentBinding>() {
    override fun getLayoutRes(): Int = R.layout.fragment_navhost_parent

    private var navController: NavController? = null

    private val nestedNavHostFragmentId = R.id.nestedParentNavHostFragment

    private val appbarViewModel by activityViewModels<ViewModel>()

//    private val viewModel:AppbarViewModel by navGraphViewModels(R.id.parent_dest)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

        // Listen on back press
//        listenOnBackPressed()
//
//        listenBackStack()

        val appBarConfig = AppBarConfiguration(navController!!.graph)
        dataBinding!!.toolbar.setupWithNavController(navController!!, appBarConfig)


        appbarViewModel.currentNavController.observe(viewLifecycleOwner, Observer { navController ->
            navController?.let {

                val appBarConfig = AppBarConfiguration(it.graph)
                dataBinding!!.toolbar.setupWithNavController(it, appBarConfig)
            }
        })

    }


//    private fun listenOnBackPressed() {
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
//    }

    override fun onResume() {
        super.onResume()
        println("🏰 ${this.javaClass.simpleName} onResume()")
//        callback.isEnabled = true
    }

    override fun onPause() {
        super.onPause()
        println("🏰 ${this.javaClass.simpleName} onPause()")
//        callback.isEnabled = false
    }

    /**
     * This callback should be created with Disabled because on rotation ViewPager creates
     * NavHost fragments that are not on screen, destroys them afterwards but it might take
     * up to 5 seconds.
     *
     * ### Note: During that interval touching back button sometimes call incorrect [OnBackPressedCallback.handleOnBackPressed] instead of this one if callback is **ENABLED**
     */

}