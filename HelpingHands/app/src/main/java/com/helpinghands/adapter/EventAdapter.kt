package com.helpinghands.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helpinghands.Fragments.OngoingEventFragment
import com.helpinghands.Fragments.PreviousEventFragment
import com.helpinghands.Fragments.RegisteredVolunteerEventsFragment
import com.helpinghands.R
import com.helpinghands.helper.Constants
import com.helpinghands.retrofit.eventsResponse
import kotlinx.android.synthetic.main.event_item.view.*

class EventAdapter(private var context: Context,
                   private var eventList: ArrayList<eventsResponse>,
                   private val mOngoingListener:
                   OngoingEventFragment.OnOngoingEventFragmentInteractionListener?,
                   private val mPreviousListener:
                   PreviousEventFragment.OnPreviousEventFragmentInteractionListener?,
                   private val mRegisteredListener:
                   RegisteredVolunteerEventsFragment.OnRegisteredVolEventFragmentInteractionListener?)
    : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
//            val item = v.tag as Event
//            // Notify the active callbacks interface (the activity, if the fragment is attached to
//            // one) that an item has been selected.
//            mListener?.onOngoingEventFragmentInteractionListener(item)
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

        val eventName           = mView.textViewEventName
        val eventLocation       = mView.textViewEventLocation
        val orgName             = mView.textViewOrgName
        val volunteers          = mView.textViewTotalvolunteers
        val category            = mView.textViewEventCategory
        val dateTime                      = mView.textViewDateTime
        val volConstrtaintLayout  =  mView.constrLayoutVolunteer


        fun bindEvents(event: eventsResponse){

            eventName.text = event.name
            eventLocation.text = event.address
            orgName.text = "event name"
            volunteers.text = event.total_volunteers
            category.text = event.category
            dateTime.text = event.date

            Log.d("*****", event.eventType.toString())
            if (event.eventType == Constants.DEFAULT_EVENT){
                volConstrtaintLayout.visibility = View.GONE
            }else{
                volConstrtaintLayout.visibility = View.VISIBLE
            }
        }

    }

}