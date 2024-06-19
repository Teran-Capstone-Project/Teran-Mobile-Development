package com.example.teran.ui.home_page.profile.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.teran.ui.home_page.profile.tab.mypost.MyPostsFragment
import com.example.teran.ui.home_page.profile.tab.myprofile.MyProfileFragment

class ViewPagerProfile(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MyProfileFragment()
            1 -> return MyPostsFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    companion object {
        const val NUM_TABS = 2
    }
}