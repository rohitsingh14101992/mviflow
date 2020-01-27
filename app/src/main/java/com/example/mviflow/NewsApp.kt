package com.example.mviflow

import android.app.Application
import com.example.mviflow.di.AppComponent
import com.example.mviflow.di.DaggerAppComponent

class NewsApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
        appComponent.inject(this)
    }

}