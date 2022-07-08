package com.rgukt.attend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rgukt.attend.fragments.AddDayFragment
import com.rgukt.attend.fragments.DeleteDayFragment
import com.rgukt.attend.fragments.ReadDayFragment

class TabPageAdapter(activity: FragmentActivity, private val tabCount: Int) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AddDayFragment()
            1 -> ReadDayFragment()
            2 -> DeleteDayFragment()
            else -> AddDayFragment()
        }
    }

}