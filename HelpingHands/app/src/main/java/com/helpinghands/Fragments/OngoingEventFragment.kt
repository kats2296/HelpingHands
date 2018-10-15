package com.helpinghands.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helpinghands.Model.Event
import com.helpinghands.R
import com.helpinghands.adapter.EventAdapter

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [OngoingEventFragment.OnListFragmentInteractionListener] interface.
 */
class OngoingEventFragment : Fragment() {

    private var columnCount = 1

    private var listener: OnOngoingEventFragmentInteractionListener? = null

    private var ongoingEventList: ArrayList<Event> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.ongoing_fragment_item_list, container, false)

        // Set the adapter

        ongoingEventList.add(Event("Food Give Away","Noida","Helping Hands",
                "16-10-2018 10:00 A.M.", 10 , "FOOD"))
        ongoingEventList.add(Event("Food Give Away","Noida","Helping Hands",
                "16-10-2018 10:00 A.M.", 10 , "FOOD"))
        ongoingEventList.add(Event("Food Give Away","Noida","Helping Hands",
                "16-10-2018 10:00 A.M.", 10 , "FOOD"))

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = EventAdapter(context, ongoingEventList, listener, null)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnOngoingEventFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnOngoingEventFragmentInteractionListener {
        fun onOngoingEventFragmentInteractionListener(item: Event?)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                OngoingEventFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
