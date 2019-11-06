package com.nikhijadhav.informationapp.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nikhijadhav.informationapp.models.InformationResponse

private const val PREFS_KEY_INFORMATION = "prefsKeyInformation"
private const val PREF_NAME = "InformationAppPrefs"
class AppPreferences(context: Context) {
    private val appContext = context.applicationContext

    private val preferences:SharedPreferences
    //get()= PreferenceManager.getDefaultSharedPreferences(appContext)
    get()= appContext.getSharedPreferences(PREF_NAME,0)

    val gson = Gson()

    fun saveInformationResponse(informationResponse: InformationResponse){
        val informationResponseString =  gson.toJson(informationResponse)
        preferences.edit().putString(
            PREFS_KEY_INFORMATION,
            informationResponseString
        ).apply()
    }

    fun getInformationResponse():InformationResponse?{
        if(preferences.contains(PREFS_KEY_INFORMATION)) {
            val informationResponseString = preferences.getString(PREFS_KEY_INFORMATION, "")
            return gson.fromJson(informationResponseString,InformationResponse::class.java)
        }
        return null
    }
}