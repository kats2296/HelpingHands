package com.helpinghands

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class OrganisationHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisation_home)
    }

    fun onClickOrgHome(view: View){

        when(view.id){

            R.id.constrLayoutOngoingEvent->{

            }

            R.id.textViewOngoingEvent->{

            }

            R.id.constrLayoutCreateEvent->{
                openProblemSelectionScreen()
            }

            R.id.textViewCreateEvent->{
                openProblemSelectionScreen()
            }

            R.id.constrLayoutPreviousEvent->{

            }

            R.id.textViewPreviousEvent->{

            }
        }
    }

    private fun openProblemSelectionScreen() {

        startActivity(Intent(this, ProblemsSelectionActivity::class.java))
    }
}
