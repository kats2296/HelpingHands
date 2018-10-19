package com.helpinghands.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.helpinghands.EventDetailsActivity
import com.helpinghands.Model.Problem
import com.helpinghands.R
import kotlinx.android.synthetic.main.problem_item.view.*


class ProblemsSelectionAdapterAdapter(private var context: Context,
                      private var problemsList: ArrayList<Problem>):
        RecyclerView.Adapter<ProblemsSelectionAdapterAdapter.problemsSelectionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): problemsSelectionViewHolder {

        val view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.problem_item, parent, false)
        return problemsSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: problemsSelectionViewHolder, position: Int) {

        if (position>=0) {
            holder.bindProblems(problemsList[position])

            holder.thumbnail.setOnClickListener {
                openEventDetailsPage()
            }
        }
    }

    private fun openEventDetailsPage() {
        context.startActivity(Intent(context, EventDetailsActivity::class.java))
    }


    override fun getItemCount(): Int {
        return problemsList.size
    }

    inner class problemsSelectionViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {


        val thumbnail : ImageView = mView.imageViewProblem


        fun bindProblems(problem: Problem) {

            thumbnail.setImageResource(problem.image)

        }
    }
}