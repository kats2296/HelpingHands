package com.helpinghands.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.helpinghands.Model.Event
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
import java.text.FieldPosition

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
var ongoingAllForVolunteer: ArrayList<eventsResponse> = ArrayList()

class VolAllOngoingEventsFragment : Fragment() {

    private var columnCount = 1
    private var type = ""

    private var listener: OnAllvolEventFragmentInteractionListener? = null


    private val apiService by lazy {
        HHApiService.create()
    }
    private lateinit var  recyclerView: RecyclerView

    private lateinit var adapter: EventAdapter
    private lateinit var progressBar: ProgressBar

    private var hhDisposable: Disposable? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.ongoing_fragment_item_list, container, false)

        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        progressBar = view.findViewById(R.id.progressBarOngoignOrg)

        adapter = EventAdapter(context!!, ongoingAllForVolunteer,null, null,
                null, listener)

        recyclerView.adapter = adapter


        if (ongoingAllForVolunteer.size==0) {
            progressBar.visibility = View.VISIBLE
            getListOfAllOngoingevents()
        }
        return view
    }

    private fun getListOfAllOngoingevents() {
        hhDisposable = apiService.getAllOngoingEventList ()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ allEvents ->
                    allEvents.all_ongoing_events.forEach {
                        it.eventType = Constants.VOLUNTEERING_EVENT
                        ongoingAllForVolunteer.add(it)
                    }
                },
                        {
                            progressBar.visibility = View.GONE
                        },
                        {
                            progressBar.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAllvolEventFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnAllvolEventFragmentInteractionListener {
        fun onAllEventFragmentInteractionListener(item: Event?)
        fun onVolunteerClick(item: eventsResponse, adapterPosition: Int)
    }

}
