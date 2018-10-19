package com.helpinghands.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.helpinghands.R
import kotlinx.android.synthetic.main.activity_volunteer_added.*

class VolunteerAddedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer_added)

        okay.setOnClickListener {
            onBackPressed()
        }
    }
}
