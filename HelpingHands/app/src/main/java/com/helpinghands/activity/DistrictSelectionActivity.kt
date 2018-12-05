
package com.helpinghands.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.helpinghands.EventDetailsActivity
import com.helpinghands.R
import kotlinx.android.synthetic.main.activity_district_selection.*

class DistrictSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_district_selection)

        // set text of each button according to obtained districts from server and current district

    }

    fun onClickDistrictSelection(view: View){

        when(view.id){

            R.id.button_district1->{
                openEventDetailsPage(button_district1.text.toString())
            }
            R.id.button_district2->{
                openEventDetailsPage(button_district2.text.toString())
            }
            R.id.button_district3->{
                openEventDetailsPage(button_district3.text.toString())
            }
            R.id.button_current_location->{
                openEventDetailsPage(button_district1.text.toString())
            }
        }
    }

    private fun openEventDetailsPage(district: String) {
        val intent  = Intent(this, EventDetailsActivity::class.java)
        intent.putExtra("district", district)
        startActivity(intent)
    }
}
