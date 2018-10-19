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
import com.helpinghands.Model.Event
import com.helpinghands.R
import com.helpinghands.adapter.EventAdapter
import com.helpinghands.database.StoreUserData
import com.helpinghands.helper.Constants
import com.helpinghands.retrofit.HHApiService
import com.helpinghands.retrofit.EmailBody
import com.helpinghands.retrofit.eventsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [OngoingEventFragment.OnListFragmentInteractionListener] interface.
 */
var ongoingEventList: ArrayList<eventsResponse> = ArrayList()

class OngoingEventFragment : Fragment() {

    private var columnCount = 1
    private var type = ""

    private var listener: OnOngoingEventFragmentInteractionListener? = null


    private val apiService by lazy {
        HHApiService.create()
    }
    private lateinit var  recyclerView: RecyclerView

    private lateinit var adapter: EventAdapter
    private lateinit var progressBar: ProgressBar

    private var hhDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            type = it.getString(TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.ongoing_fragment_item_list, container, false)


        var eventType = ""
        if (type == Constants.VOLUNTEERING_EVENT){
            eventType = Constants.VOLUNTEERING_EVENT
        }else{
            eventType = Constants.DEFAULT_EVENT
        }

        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        progressBar = view.findViewById(R.id.progressBarOngoignOrg)

        adapter = EventAdapter(context!!, ongoingEventList, listener, null, null,
                null)

        recyclerView.adapter = adapter


        if (ongoingEventList.size==0) {
            progressBar.visibility = View.VISIBLE
            getListOfOngoingEvents(eventType)
        }
        return view
    }

    private fun getListOfOngoingEvents(eventType: String) {

        hhDisposable = apiService.getOngoignEventList(email = EmailBody(StoreUserData.getInstance(context)
                .email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ ongoingsEvents ->
                    ongoingsEvents.ongoing_events.forEach {
                        it.eventType = Constants.DEFAULT_EVENT
                        ongoingEventList.add(it)
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
        const val TYPE = "type"

        @JvmStatic
        fun newInstance(columnCount: Int, type: String) =
                OngoingEventFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                        putString(TYPE, type)
                    }
                }
    }
}
