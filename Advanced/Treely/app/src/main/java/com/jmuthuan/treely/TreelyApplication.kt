package com.jmuthuan.treely

import android.app.Application
import com.jmuthuan.treely.data.AppDataContainer

class TreelyApplication: Application() {
    lateinit var dataConteiner: AppDataContainer

    override fun onCreate() {
        super.onCreate()
        dataConteiner = AppDataContainer()
    }
}