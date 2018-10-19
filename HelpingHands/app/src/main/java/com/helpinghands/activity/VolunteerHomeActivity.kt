package com.helpinghands.activity

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.helpinghands.Fragments.OngoingEventFragment
import com.helpinghands.Fragments.RegisteredVolunteerEventsFragment
import com.helpinghands.Fragments.VolAllOngoingEventsFragment
import com.helpinghands.Fragments.registeredEvents
import com.helpinghands.Model.Event
import com.helpinghands.R
import com.helpinghands.adapter.PagerAdapter
import com.helpinghands.helper.Constants
import com.helpinghands.retrofit.eventsResponse
import kotlinx.android.synthetic.main.activity_volunteer_home.*

class VolunteerHomeActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener,
OngoingEventFragment.OnOngoingEventFragmentInteractionListener,
RegisteredVolunteerEventsFragment.OnRegisteredVolEventFragmentInteractionListener,
VolAllOngoingEventsFragment.OnAllvolEventFragmentInteractionListener{

    override fun onAllEventFragmentInteractionListener(item: Event?) {
        Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT).show()
    }

    override fun onVolunteerClick(item: eventsResponse, adapterPosition: Int) {
        if(!registeredEvents.contains(item)) {
            registeredEvents.add(item)
        }
        startActivity(Intent(this, VolunteerAddedActivity::class.java))
    }


    override fun onRegisteredVolEventFragmentInteractionListener(uri: Uri) {

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
        adapter.addFragment(VolAllOngoingEventsFragment(),
                "Ongoing Events")
        adapter.addFragment(RegisteredVolunteerEventsFragment.
                newInstance("two","two"), "Registered Events")
        view_pager?.adapter = adapter

    }
}
