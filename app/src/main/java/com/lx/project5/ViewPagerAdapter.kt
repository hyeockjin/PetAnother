package com.lx.project5

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: SplashActivity): FragmentStateAdapter(fa) {
    var fragments = listOf<Fragment>()

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment {
        return fragments.get(position)
    }
}