package org.wit.inventory.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.inventory.models.BuildingJSONStore
import org.wit.inventory.models.BuildingMemStore
import org.wit.inventory.models.BuildingStore


class MainApp : Application(), AnkoLogger {

    lateinit var buildings: BuildingStore

    override fun onCreate() {
        super.onCreate()
        buildings = BuildingJSONStore(applicationContext)
        info("Inventory started")
    }
}