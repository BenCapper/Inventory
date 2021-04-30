package org.wit.inventory.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_building_list.recyclerView
import kotlinx.android.synthetic.main.activity_building_list.toolbar
import kotlinx.android.synthetic.main.activity_stock_list.*
import org.jetbrains.anko.intentFor
import org.wit.inventory.R
import org.wit.inventory.main.MainApp
import org.wit.inventory.models.BuildingModel
import org.wit.inventory.models.StockModel
import java.util.*


class StockListActivity : AppCompatActivity(), StockListener {
    private companion object{
        private const val TAG = "StockListActivity"
    }
    private lateinit var auth: FirebaseAuth
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_list)
        app = application as MainApp
        auth = Firebase.auth
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadBranchStock()
        val branchStock = intent.extras?.getParcelable<BuildingModel>("branchName")!!
        toolbar.title = branchStock.name + " " + "Stock"
        setSupportActionBar(toolbar)

        //https://stackoverflow.com/questions/55949305/how-to-properly-retrieve-data-from-searchview-in-kotlin
        stockSearch.setOnQueryTextListener(object :  SearchView.OnQueryTextListener  {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val branchStock = intent.extras?.getParcelable<BuildingModel>("branchName")!!
                if (newText != null) {
                    showStock(app.stock.findByBranchId(branchStock.id).filter { s -> s.name.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT)) })
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
        if(item.itemId == R.id.logout){
            Log.i(TAG, "Logout")
            //Logout the User
            auth.signOut()
            val logoutIntent = Intent(this, LoginActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
        }
        val branchStock = intent.extras?.getParcelable<BuildingModel>("branchName")!!
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
        app.stock.update(stock)
        loadBranchStock()
    }

    override fun onMinusStockClick(stock: StockModel) {
        stock.inStock--
        app.stock.update(stock)
        loadBranchStock()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadBranchStock()
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun loadBranchStock(){
        val branchStock = intent.extras?.getParcelable<BuildingModel>("branchName")!!
        showStock(app.stock.findByBranchId(branchStock.id))
    }

    private fun showStock (stock: List<StockModel>) {
        recyclerView.adapter = StockAdapter(stock, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

