package org.wit.inventory.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class BuildingMemStore : BuildingStore, AnkoLogger {

    private val buildings = ArrayList<BuildingModel>()

    override fun findAll(): List<BuildingModel> {
        return buildings
    }

    override fun create(building: BuildingModel) {
        building.id = getId()
        buildings.add(building)
        logAll()
    }

    override fun update(building: BuildingModel) {
        var foundBuilding: BuildingModel? = buildings.find { p -> p.id == building.id }
        if (foundBuilding != null) {
            foundBuilding.name = building.name
            foundBuilding.address = building.address
            foundBuilding.image = building.image
            logAll()
        }
    }

    fun logAll() {
        buildings.forEach{ info("${it}") }
    }

}