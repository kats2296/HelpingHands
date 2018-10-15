package com.helpinghands.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private  var fragmentList: ArrayList<Fragment> = ArrayList(2)
    private  var fragmentTitles: ArrayList<String> = ArrayList(2)

    fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitles.add(title)
    }

    override fun getItem(position: Int): Fragment? {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitles.get(position)
    }
}