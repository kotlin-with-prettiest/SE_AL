package com.example.seal.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.seal.CalendarItemFragment
import com.example.seal.LoginFragment
import com.example.seal.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, val fragmentCount : Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {

            when (position) {
                0 -> return AddAlarmFragment()
                1 -> return CalendarItemFragment()
                else -> return FixAlarmFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return context.resources.getString(TAB_TITLES[position])
        }

        override fun getCount(): Int = fragmentCount

}