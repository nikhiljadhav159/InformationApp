package com.nikhijadhav.informationapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nikhijadhav.informationapp.apis.InformationApi
import com.nikhijadhav.informationapp.models.InformationResponse
import com.nikhijadhav.informationapp.models.Row
import com.nikhijadhav.informationapp.sharedpreferences.AppPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException


open class MainViewModel(var savedState: SavedStateHandle) : ViewModel() {
    val TAG = MainViewModel::class.java.simpleName
    var retriver = InformationApi()
    val title = MutableLiveData<String>()
    val noInternetError = MutableLiveData<Boolean>()
    val informationList = MutableLiveData<ArrayList<Row>>()
    lateinit var preferences: AppPreferences

    companion object {
        val KEY_TITLE = "Title"
        val KEY_INFORMATION_LIST = "InformationList"
    }

    /**
     * Get the title livedata from SavedStateHandle
     */
    fun getTitleLiveData(): MutableLiveData<String> {

        return savedState.getLiveData(KEY_TITLE)
    }

    /**
     * Get the arraylist from SavedStateHandle
     */
    fun getInformationListLiveData(): MutableLiveData<ArrayList<Row>> {
        return savedState.getLiveData(KEY_INFORMATION_LIST)
    }


    fun callForInformationData() {
        //region creating a callback for response
        val callback = object : Callback<InformationResponse> {

            override fun onResponse(call: Call<InformationResponse>?, response: Response<InformationResponse>?) {
                val informationResponse = response?.body()!!
                preferences.saveInformationResponse(informationResponse)
                postInformationResponse(informationResponse)
            }

            override fun onFailure(call: Call<InformationResponse>?, t: Throwable?) {
                Log.e(TAG, t.toString())
                val prefInformationResponse = preferences.getInformationResponse()
                if(t is UnknownHostException){
                    if(prefInformationResponse!=null) {
                        postInformationResponse(prefInformationResponse)
                    }else{
                        noInternetError.postValue(true)
                    }
                }else{
                    noInternetError.postValue(false)
                }
            }
        }
        //endregion

        //region create request call and handle the callback
        retriver.getInformation(callback)
        //endregion
    }

    private fun postInformationResponse(
        informationResponse: InformationResponse
    ) {
        savedState.set(KEY_INFORMATION_LIST, informationResponse.rows)
        savedState.set(KEY_TITLE, informationResponse.title)
    }

}
