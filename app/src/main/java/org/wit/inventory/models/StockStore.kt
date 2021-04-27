package org.wit.inventory.models

interface StockStore {
    fun findAll(): List<StockModel>
    fun create(stock: StockModel)
    fun update(stock: StockModel)
    fun delete(stock: StockModel)
    fun findByBranchId(id: Long): List<StockModel>
}