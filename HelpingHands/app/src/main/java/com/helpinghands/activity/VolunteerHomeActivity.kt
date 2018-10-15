package com.helpinghands.activity

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.helpinghands.Fragments.OngoingEventFragment
import com.helpinghands.Fragments.RegisteredVolunteerEventsFragment
import com.helpinghands.Model.Event
import com.helpinghands.R
import com.helpinghands.adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_volunteer_home.*

class VolunteerHomeActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener,
OngoingEventFragment.OnOngoingEventFragmentInteractionListener,
RegisteredVolunteerEventsFragment.OnFragmentInteractionListener{


    override fun onFragmentInteraction(uri: Uri) {

    }


    override fun onOngoingEventFragmentInteractionListener(item: Event?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tabLayout.getTabAt(tab!!.position)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer_home)

        setupViewPager(view_pager)
        tabLayout.setupWithViewPager(view_pager)
        tabLayout.setOnTabSelectedListener(this)
    }

    private fun setupViewPager(view_pager: ViewPager?) {

        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(OngoingEventFragment.newInstance(1), "Ongoing Events")
        adapter.addFragment(RegisteredVolunteerEventsFragment.
                newInstance("two","two"), "Registered Events")
        view_pager?.adapter = adapter

    }
}
