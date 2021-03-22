package org.wit.inventory.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.inventory.models.BuildingMemStore


class MainApp : Application(), AnkoLogger {

    val buildings = BuildingMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Inventory started")
    }
}