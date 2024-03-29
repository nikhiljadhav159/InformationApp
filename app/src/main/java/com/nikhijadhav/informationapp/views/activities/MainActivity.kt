package com.nikhijadhav.informationapp.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModelProviders
import com.nikhijadhav.informationapp.R
import com.nikhijadhav.informationapp.sharedpreferences.AppPreferences
import com.nikhijadhav.informationapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tbMain.title = getString(R.string.app_name)
        setSupportActionBar(tbMain as Toolbar)
        mainViewModel = ViewModelProviders.of(this, SavedStateVMFactory(this)).get(MainViewModel::class.java)
        preferences = AppPreferences(this)
        mainViewModel.preferences = preferences
        mainViewModel.getTitleLiveData().observe(this, Observer { tbMain.title = it })


    }
}
