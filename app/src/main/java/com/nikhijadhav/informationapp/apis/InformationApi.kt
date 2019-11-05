package com.nikhijadhav.informationapp.apis

import com.nikhijadhav.informationapp.models.InformationResponse
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class InformationApi {
    private var service : InformationData

    init {
        //region Setting up the okHttpClient for retrofit
        val okHttp: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .build()
        //endregion

        //region Setting up the retrofit request
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/")
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //endregion

        //region creating a retrofit service request for InformationData class
        service = retrofit.create(InformationData::class.java)
        //endregion
    }

    fun getInformation(callback: Callback<InformationResponse>){
        //region creating call for service request and handle the call back from request
        val call = service.retriveInformation()
        call.enqueue(callback)
        //endregion
    }
}