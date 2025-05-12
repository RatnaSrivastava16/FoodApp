package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.MenuItemBinding

class MenuAdapter(private val menuItems:MutableList<String>,private val menuItemPrice:MutableList<String>,private val menuItemImage:MutableList<Int>): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int=menuItems.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class MenuViewHolder (private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                menufoodName.text = menuItems[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(menuItemImage[position])
            }
        }
    }
}