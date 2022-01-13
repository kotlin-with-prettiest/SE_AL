package com.example.seal.navhost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.seal.R
import com.example.seal.databinding.FragmentNavhostHomeBinding
import com.example.seal.ui.main.BaseDataBindingFragment
import com.example.seal.viewModel.ViewModel

class HomeNavHostFragment : BaseDataBindingFragment<FragmentNavhostHomeBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_navhost_home

    private val viewModel by activityViewModels<ViewModel>()

    private var navController: NavController? = null

    private val nestedNavHostFragmentId = R.id.nestedHomeNavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("🏠 ${this.javaClass.simpleName} #${this.hashCode()}  onCreate()")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("🏠 ${this.javaClass.simpleName} #${this.hashCode()}  onViewCreated()")

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
        println("🏠 ${this.javaClass.simpleName} #${this.hashCode()}  onResume()")

//        callback.isEnabled = true

        // Set this navController as ViewModel's navController
        navController?.let {
            viewModel.currentNavController.value = navController
//            viewModel.currentNavController.value = Event(it)
        }

    }

    override fun onPause() {
        super.onPause()
//        callback.isEnabled = false
        println("🏠 ${this.javaClass.simpleName} #${this.hashCode()}  onPause()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("🏠 ${this.javaClass.simpleName} #${this.hashCode()}  onDestroyView()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("🏠 ${this.javaClass.simpleName} #${this.hashCode()}  onDestroy()")
    }
}