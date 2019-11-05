package com.nikhijadhav.informationapp.apis

import com.nikhijadhav.informationapp.models.InformationResponse
import retrofit2.Call
import retrofit2.http.GET

interface InformationData {

    @GET("facts.json")
    fun retriveInformation(): Call<InformationResponse>
}