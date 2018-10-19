package com.helpinghands.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.helpinghands.R
import com.helpinghands.adapter.EventAdapter
import com.helpinghands.database.StoreUserData
import com.helpinghands.helper.Constants
import com.helpinghands.retrofit.EmailBody
import com.helpinghands.retrofit.HHApiService
import com.helpinghands.retrofit.eventsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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

var ongoingAllForVolunteer: ArrayList<eventsResponse> = ArrayList()
class RegisteredVolunteerEventsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var mListenerRegisteredVolEvent: OnRegisteredVolEventFragmentInteractionListener? = null

    private var registeredEventList: ArrayList<eventsResponse> = ArrayList()

    private lateinit var  recyclerView: RecyclerView

    private lateinit var adapter: EventAdapter
    private val apiService by lazy {
        HHApiService.create()
    }
    private lateinit var progressBar: ProgressBar

    private var hhDisposable: Disposable? = null

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
        progressBar = view.findViewById(R.id.progressBarVolOngoingAll)

        adapter = EventAdapter(context!!, registeredEventList, null,null,
                mListenerRegisteredVolEvent)

        recyclerView.adapter = adapter

        if (ongoingAllForVolunteer.size==0) {
            progressBar.visibility = View.VISIBLE
            getListOfAllOngoingEvents()
        }
        return view

    }

    private fun getListOfAllOngoingEvents() {
        hhDisposable = apiService.getAllOngoingEventList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ allOngoingEvents ->
                    allOngoingEvents.all_ongoing_events.forEach {
                        it.eventType = Constants.VOLUNTEERING_EVENT
                        ongoingAllForVolunteer.add(it)
                    }
                },
                        {
                            progressBar.visibility = View.GONE
                        },
                        {
                            Log.d("EVENTS", ongoingEventList.size.toString())
                            progressBar.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        })
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
