package org.wit.inventory.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.inventory.models.*


class MainApp : Application(), AnkoLogger {
    lateinit var buildings: BuildingStore
    lateinit var stock: StockStore


    override fun onCreate() {
        super.onCreate()
        buildings = BuildingJSONStore(applicationContext)
        stock = StockJSONStore(applicationContext)
        info("Inventory started")
    }
}