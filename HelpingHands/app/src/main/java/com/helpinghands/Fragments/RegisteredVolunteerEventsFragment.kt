package com.helpinghands.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helpinghands.Model.Event

import com.helpinghands.R
import com.helpinghands.adapter.EventAdapter

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RegisteredVolunteerEventsFragment.OnRegisteredVolEventFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RegisteredVolunteerEventsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RegisteredVolunteerEventsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var mListenerRegisteredVolEvent: OnRegisteredVolEventFragmentInteractionListener? = null

    private var registeredEventList: ArrayList<Event> = ArrayList()

    private lateinit var  recyclerView: RecyclerView

    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_registered_volunteer_events,
                container, false)

        recyclerView = view.findViewById(R.id.recylerViewRegisteredVolEvent)
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        registeredEventList.add(Event("Food Give Away","Noida","Helping Hands",
                "16-10-2018 10:00 A.M.", 10 , "FOOD"))

        registeredEventList.add(Event("Food Give Away","Noida","Helping Hands",
                "16-10-2018 10:00 A.M.", 10 , "FOOD"))

        registeredEventList.add(Event("Food Give Away","Noida","Helping Hands",
                "16-10-2018 10:00 A.M.", 10 , "FOOD"))

        adapter = EventAdapter(context!!, registeredEventList, null,
                mListenerRegisteredVolEvent)

        recyclerView.adapter = adapter

        return view

    }

    fun onButtonPressed(uri: Uri) {
        mListenerRegisteredVolEvent?.onRegisteredVolEventFragmentInteractionListener(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRegisteredVolEventFragmentInteractionListener) {
            mListenerRegisteredVolEvent = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnRegisteredVolEventFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListenerRegisteredVolEvent = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnRegisteredVolEventFragmentInteractionListener {
        fun onRegisteredVolEventFragmentInteractionListener(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisteredVolunteerEventsFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                RegisteredVolunteerEventsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
