package org.wit.inventory.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_building_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.inventory.R
import org.wit.inventory.main.MainApp
import org.wit.inventory.models.BuildingModel


class BuildingListActivity : AppCompatActivity(), BuildingListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadBuildings()

        toolbar.title = title
        setSupportActionBar(toolbar)

        //https://stackoverflow.com/questions/55949305/how-to-properly-retrieve-data-from-searchview-in-kotlin
        buildingSearch.setOnQueryTextListener(object :  SearchView.OnQueryTextListener  {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    showBuildings(app.buildings.filterBuildings(newText))
                }
                return false
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<BuildingActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBuildingClick(building: BuildingModel) {
        startActivityForResult(intentFor<StockListActivity>().putExtra("branchName", building), 0)
    }

    override fun onEditBuildingClick(building: BuildingModel) {
        startActivityForResult(intentFor<BuildingActivity>().putExtra("building_edit", building), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadBuildings()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadBuildings() {
        showBuildings(app.buildings.findAll())
    }

    private fun showBuildings (buildings: List<BuildingModel>) {
        recyclerView.adapter = BuildingAdapter(buildings, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

