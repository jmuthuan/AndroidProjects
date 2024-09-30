package com.example.mynotks

import android.app.Application
import com.example.mynotks.data.AppContainer
import com.example.mynotks.data.AppDataContainer

class MyNotksApplication: Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}