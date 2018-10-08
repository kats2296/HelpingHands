package com.helpinghands

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_problems_selection.*

class ProblemsSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problems_selection)

        val problemList = arrayListOf(Problem(R.mipmap.zero_hunger, "Food"),
                Problem(R.mipmap.poverty, "Clothing"),
                Problem(R.mipmap.poverty, "Donation"),
                Problem(R.mipmap.medical_aids, "Medical-Aids"),
                Problem(R.mipmap.education, "Education"))


        recyclerViewProblems.layoutManager = LinearLayoutManager(this)

        val adapter = ProblemsSelectionAdapterAdapter(this, problemList)

        recyclerViewProblems.adapter = adapter

    }
}
