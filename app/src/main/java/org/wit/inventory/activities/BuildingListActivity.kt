package org.wit.inventory.activities

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_building_list.*
import org.jetbrains.anko.intentFor
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
        recyclerView.adapter = BuildingAdapter(app.buildings.findAll(), this)

        toolbar.title = title
        setSupportActionBar(toolbar)
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

    override fun onBuildingClick(placemark: BuildingModel) {
        startActivityForResult(intentFor<BuildingActivity>().putExtra("building_edit", placemark), 0)
    }
}

