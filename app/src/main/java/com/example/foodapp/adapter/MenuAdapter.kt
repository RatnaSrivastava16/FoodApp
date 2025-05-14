package com.example.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.DetailsActivity
import com.example.foodapp.databinding.MenuItemBinding

class MenuAdapter(private val menuItems:MutableList<String>,private val menuItemPrice:MutableList<String>,private val menuItemImage:MutableList<Int>,private val requireContext: Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

private val itemClickListener: View.OnClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int=menuItems.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class MenuViewHolder (private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                {
                    itemClickListener.onItemClick(position)
                }
                val intent=Intent(requireContext, DetailsActivity::class.java)
                intent.putExtra("MenuItemName",menuItems.get(position))
                intent.putExtra("MenuItemImage",menuItemImage.get(position))
                requireContext.startActivity(intent)

            }
        }
        fun bind(position: Int){
            binding.apply {
                menufoodName.text = menuItems[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(menuItemImage[position])

            }
        }
    }
}

private fun View.OnClickListener?.onItemClick(position: Int) {

}


