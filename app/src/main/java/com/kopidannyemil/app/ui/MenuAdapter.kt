package com.kopidannyemil.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kopidannyemil.app.data.AppRepository
import com.kopidannyemil.app.data.MenuItem
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.databinding.ItemMenuBinding

class MenuAdapter(
    private var items: List<MenuItem>,
    private val onCartChanged: () -> Unit
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    fun submit(newItems: List<MenuItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val b = holder.binding

        b.ivPhoto.load(item.image) { crossfade(true) }
        b.tvName.text = item.name
        b.tvDesc.text = item.description
        b.tvPrice.text = item.price.toRupiah()
        bindQuantity(b, item)

        b.btnAdd.setOnClickListener { change(holder, item, +1) }
        b.btnPlus.setOnClickListener { change(holder, item, +1) }
        b.btnMinus.setOnClickListener { change(holder, item, -1) }
    }

    private fun change(holder: ViewHolder, item: MenuItem, delta: Int) {
        if (delta > 0) AppRepository.add(item.id) else AppRepository.remove(item.id)
        bindQuantity(holder.binding, item)
        onCartChanged()
    }

    private fun bindQuantity(b: ItemMenuBinding, item: MenuItem) {
        val qty = AppRepository.quantityOf(item.id)
        if (qty == 0) {
            b.btnAdd.visibility = View.VISIBLE
            b.qtyControls.visibility = View.GONE
        } else {
            b.btnAdd.visibility = View.GONE
            b.qtyControls.visibility = View.VISIBLE
            b.tvQty.text = qty.toString()
        }
    }
}
