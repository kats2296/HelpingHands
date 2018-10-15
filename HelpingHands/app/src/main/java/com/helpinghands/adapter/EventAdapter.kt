package com.helpinghands.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helpinghands.Model.Event
import com.helpinghands.Fragments.OngoingEventFragment
import com.helpinghands.R
import kotlinx.android.synthetic.main.event_item.view.*

class EventAdapter(private var context: Context,
                   private var eventList: ArrayList<Event>,
                   private val mListener: OngoingEventFragment.OnOngoingEventFragmentInteractionListener?)
    : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Event
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onOngoingEventFragmentInteractionListener(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {

        val view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false)

        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        holder.bindEvents(eventList[position])

    }

    override fun getItemCount(): Int  = eventList.size

    inner class EventViewHolder(val mView: View): RecyclerView.ViewHolder(mView){

        val eventImage         = mView.imageViewEvent
        val eventName           = mView.textViewEventName
        val eventLocation       = mView.textViewEventLocation
        val orgName             = mView.textViewOrgName
        val volunteers          = mView.textViewTotalvolunteers
        val category            = mView.textViewEventCategory
        val dateTime                      = mView.textViewDateTime


        fun bindEvents(event: Event){

            eventName.text = event.eventName
            eventLocation.text = event.eventLocation
            orgName.text = event.orgName
            volunteers.text = event.volunteers.toString()
            category.text = event.category
            dateTime.text = event.dateTime
        }

    }

}