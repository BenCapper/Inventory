package org.wit.inventory.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_building_list.*
import kotlinx.android.synthetic.main.card_stock.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.inventory.R
import org.wit.inventory.main.MainApp
import org.wit.inventory.models.BuildingModel
import org.wit.inventory.models.StockModel


class StockListActivity : AppCompatActivity(), StockListener {

    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadBranchStock()
        var branchStock = intent.extras?.getParcelable<BuildingModel>("branchName")!!
        toolbar.title = branchStock.name + " " + "Stock"
        setSupportActionBar(toolbar)


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var branchStock = intent.extras?.getParcelable<BuildingModel>("branchName")!!
        when (item.itemId) {
            R.id.item_add -> startActivityForResult(intentFor<StockActivity>().putExtra("branchName", branchStock), 0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStockClick(stock: StockModel) {
        startActivityForResult(intentFor<StockActivity>().putExtra("stock_edit", stock), 0)
    }

    override fun onAddStockClick(stock: StockModel) {
        stock.inStock++
        loadBranchStock()
    }

    override fun onMinusStockClick(stock: StockModel) {
        stock.inStock--
        loadBranchStock()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadBranchStock()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadStock() {
        showStock(app.stock.findAll())
    }

    private fun loadBranchStock(){
        var branchStock = intent.extras?.getParcelable<BuildingModel>("branchName")!!
        showStock(app.stock.findByBranchId(branchStock.id))
    }

    private fun showStock (stock: List<StockModel>) {
        recyclerView.adapter = StockAdapter(stock, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

