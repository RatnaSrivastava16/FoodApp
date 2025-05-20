package com.example.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.DetailsActivity
import com.example.foodapp.databinding.MenuItemBinding
import com.example.foodapp.model.MenuItem

class MenuAdapter(private val menuItems:List<MenuItem>, private val requireContext: Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

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
                    openDetailsActivity(position)
                }
            }
        }
        private fun openDetailsActivity(position: Int)
        {
            val menuItem=menuItems[position]
            val intent=Intent(requireContext,DetailsActivity::class.java)
            intent.putExtra("MenuItemName",menuItem.name)
            intent.putExtra("MenuItemImage",menuItem.imageUrl)
            intent.putExtra("MenuItemPrice",menuItem.price)
            intent.putExtra("MenuItemDescription",menuItem.description)
            intent.putExtra("MenuItemIngredients",menuItem.ingredients)
            requireContext.startActivity(intent)
        }

        fun bind(position: Int){
            val menuItem=menuItems[position]
            binding.apply {
                menufoodName.text = menuItem.name
                menuPrice.text = menuItem.price
                val Uri= Uri.parse(menuItem.imageUrl)
                Glide.with(requireContext).load(Uri).into(menuImage)


            }
        }
    }
}

private fun View.OnClickListener?.onItemClick(position: Int) {

}


