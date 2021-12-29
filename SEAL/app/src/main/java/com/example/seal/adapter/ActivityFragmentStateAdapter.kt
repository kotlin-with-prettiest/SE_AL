package com.example.seal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter.FragmentTransactionCallback.OnPostEventListener
import com.example.seal.navhost.HomeNavHostFragment
import com.example.seal.ui.main.ClassListFragment
import com.example.seal.ui.main.HomeFragment
import com.example.seal.ui.main.SettingFragment


class ActivityFragmentStateAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    init {
        // Add a FragmentTransactionCallback to handle changing
        // the primary navigation fragment
        registerFragmentTransactionCallback(object : FragmentTransactionCallback() {
            override fun onFragmentMaxLifecyclePreUpdated(
                fragment: Fragment,
                maxLifecycleState: Lifecycle.State
            ) = if (maxLifecycleState == Lifecycle.State.RESUMED) {

                // This fragment is becoming the active Fragment - set it to
                // the primary navigation fragment in the OnPostEventListener
                OnPostEventListener {
                    fragment.parentFragmentManager.commitNow {
                        setPrimaryNavigationFragment(fragment)
                    }
                }

            } else {
                super.onFragmentMaxLifecyclePreUpdated(fragment, maxLifecycleState)
            }
        })
    }

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> return ClassListFragment()
            1 -> return HomeFragment()
            else -> return SettingFragment()
        }
    }
}




//class ActivityFragmentStateAdapter (fragmentActivity: FragmentActivity) :
//    FragmentStateAdapter(fragmentActivity) {
//
//    init {
//        // Add a FragmentTransactionCallback to handle changing
//        // the primary navigation fragment
//        registerFragmentTransactionCallback(object : FragmentTransactionCallback() {
//            override fun onFragmentMaxLifecyclePreUpdated(
//                fragment: Fragment,
//                maxLifecycleState: Lifecycle.State,
//            ) = if (maxLifecycleState == Lifecycle.State.RESUMED) {
//
//                // This fragment is becoming the active Fragment - set it to
//                // the primary navigation fragment in the OnPostEventListener
//                OnPostEventListener {
//                    fragment.parentFragmentManager.commitNow {
//                        setPrimaryNavigationFragment(fragment)
//                    }
//                }
//
//            } else {
//                super.onFragmentMaxLifecyclePreUpdated(fragment, maxLifecycleState)
//            }
//        })
//    }
//
//    override fun getItemCount(): Int=3
//
//    override fun createFragment(position: Int): Fragment {
//        return when (position) {
//            0 -> return ClassListFragment()
//            1 -> return HomeFragment()
//            else -> return SettingFragment()
//        }
//    }
//
//
//}