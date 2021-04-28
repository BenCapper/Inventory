package org.wit.inventory.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_stock.view.*
import org.wit.inventory.R
import org.wit.inventory.helpers.readImageFromPath
import org.wit.inventory.models.BuildingModel
import org.wit.inventory.models.StockModel

interface StockListener {
    fun onStockClick(stock: StockModel)
    fun onAddStockClick(stock: StockModel)
    fun onMinusStockClick(stock: StockModel)
}

class StockAdapter constructor(private var stocks: List<StockModel>,
                                  private val listener: StockListener) :
    RecyclerView.Adapter<StockAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_stock,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val stock = stocks[holder.adapterPosition]
        holder.bind(stock, listener)
    }

    override fun getItemCount(): Int = stocks.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(stock: StockModel, listener : StockListener) {
            itemView.stockListName.text = stock.name.capitalize()
            itemView.stockListWeight.text = stock.weight
            itemView.stockListPrice.text = stock.price.toString()
            itemView.stockListDept.text = stock.dept.capitalize()
            itemView.inStockNum.text = stock.inStock.toString()
            itemView.stockImageIcon.setImageBitmap(readImageFromPath(itemView.context, stock.image))
            itemView.setOnClickListener {listener.onStockClick(stock)}
            itemView.add.setOnClickListener {listener.onAddStockClick(stock)}
            itemView.minus.setOnClickListener {listener.onMinusStockClick(stock)}
        }
    }


}
