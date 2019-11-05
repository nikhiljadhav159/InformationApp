package com.nikhijadhav.informationapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nikhijadhav.informationapp.apis.InformationApi
import com.nikhijadhav.informationapp.models.InformationResponse
import com.nikhijadhav.informationapp.models.Row
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class MainViewModel(private val savedState: SavedStateHandle) : ViewModel() {
    val TAG = MainViewModel::class.java.simpleName

    companion object {
        val KEY_TITLE = "Title"
        val KEY_INFORMATION_LIST = "InformationList"
    }

    fun getTitle(): MutableLiveData<String> {
        return savedState.getLiveData(KEY_TITLE)
    }

    fun getInformationList(): MutableLiveData<ArrayList<Row>> {
        return savedState.getLiveData(KEY_INFORMATION_LIST)
    }

    fun callForInformationData() {
        //region creating a callback for response
        val callback = object : Callback<InformationResponse> {

            override fun onResponse(call: Call<InformationResponse>?, response: Response<InformationResponse>?) {
                val informationResponse = response?.body()!!
                savedState.set(KEY_TITLE, informationResponse.title)
                savedState.set(KEY_INFORMATION_LIST, informationResponse.rows as ArrayList<Row>)
            }

            override fun onFailure(call: Call<InformationResponse>?, t: Throwable?) {
                Log.e(TAG, t.toString())
            }
        }
        //endregion

        //region create request call and handle the callback
        val retriver = InformationApi()
        retriver.getInformation(callback)
        //endregion
    }

}
