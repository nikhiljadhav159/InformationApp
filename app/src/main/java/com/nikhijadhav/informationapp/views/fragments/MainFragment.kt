package com.nikhijadhav.informationapp.views.fragments


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikhijadhav.informationapp.R
import com.nikhijadhav.informationapp.adapters.InformationRecyclerViewAdapter
import com.nikhijadhav.informationapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    private lateinit var mainViewModel:MainViewModel
    private val informationRecyclerViewAdapter = InformationRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srlMain.setOnRefreshListener{
            if(isConnected()) {
                Toast.makeText(activity!!, getString(R.string.refreshing), Toast.LENGTH_SHORT).show()
                mainViewModel.callForInformationData()
            }else{
                Toast.makeText(activity!!, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
              srlMain.isRefreshing = false
            }
        }
        rvMain.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL,false)
        rvMain.adapter = informationRecyclerViewAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = activity?.run {
            ViewModelProviders.of(this, SavedStateVMFactory(this)).get(MainViewModel::class.java)
        } ?: throw Exception(getString(R.string.invaid_activity))

        mainViewModel.getInformationList().observe(this, Observer { list->
            srlMain.isRefreshing = false
            run {
               informationRecyclerViewAdapter.updateInformationList(list)
            }
        })

        if(isConnected()) {
            mainViewModel.callForInformationData()
        }else{
            Toast.makeText(activity!!, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }

    }

    fun isConnected(): Boolean {
        val cm = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}
