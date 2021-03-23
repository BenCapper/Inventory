package org.wit.inventory.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_building.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.inventory.R
import org.wit.inventory.helpers.readImage
import org.wit.inventory.helpers.readImageFromPath
import org.wit.inventory.helpers.showImagePicker
import org.wit.inventory.main.MainApp
import org.wit.inventory.models.BuildingModel

class BuildingActivity : AppCompatActivity(), AnkoLogger {

    var building = BuildingModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)
        app = application as MainApp
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        if (intent.hasExtra("building_edit")) {
            building = intent.extras?.getParcelable<BuildingModel>("building_edit")!!
            buildingImage.setImageBitmap(readImageFromPath(this, building.image))
            if (building.image != null) {
                chooseImage.setText(R.string.change_building_image)
            }
            btnAdd.setText(R.string.save_building)
            buildingName.setText(building.name)
            buildingAddress.setText(building.address)
        }

        btnAdd.setOnClickListener() {
            building.name = buildingName.text.toString()
            building.address = buildingAddress.text.toString()
            if (building.name.isEmpty()) {
                toast(R.string.enter_building_name)
            } else {
                if (edit) {
                    app.buildings.update(building.copy())
                } else {
                    app.buildings.create(building.copy())
                }
            }
            info("add Button Pressed: $buildingName")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        buildingLocation.setOnClickListener {
            info ("Set Location Pressed")
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_building, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    building.image = data.getData().toString()
                    buildingImage.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }
}