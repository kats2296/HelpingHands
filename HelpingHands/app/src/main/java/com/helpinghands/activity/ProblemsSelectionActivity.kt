package com.helpinghands.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.helpinghands.Model.Problem
import com.helpinghands.R
import com.helpinghands.adapter.ProblemsSelectionAdapterAdapter
import kotlinx.android.synthetic.main.activity_problems_selection.*

class ProblemsSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problems_selection)

        val problemList = arrayListOf(Problem(R.drawable.ic_zero_hunger, "Food"),
                Problem(R.drawable.ic_no_poverty, "Clothing"),
                Problem(R.drawable.ic_no_poverty, "Donation"),
                Problem(R.drawable.ic_medical_aids, "Medical-Aids"),
                Problem(R.drawable.ic_education, "Education"))


        recyclerViewProblems.layoutManager = LinearLayoutManager(this)

        val adapter = ProblemsSelectionAdapterAdapter(this, problemList)

        recyclerViewProblems.adapter = adapter

    }
}
