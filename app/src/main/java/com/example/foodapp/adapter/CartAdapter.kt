package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.CartItemBinding

class CartAdapter(private val cartItems: MutableList<String>,private val CartitemPrice: MutableList<String>,private var CartImage: MutableList<Int>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val itemQuantites = IntArray(cartItems.size) { 1 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quatity = itemQuantites[position]
                cartFoodName.text = cartItems[position]
                cartItemPrice.text = CartitemPrice[position]
                cartImage.setImageResource(CartImage[position])
                cartItemQuantity.text = quatity.toString()
                minusbtn.setOnClickListener {
                    decreaseQuantity(position)

                }
                plusbn.setOnClickListener {
                    increaseQuantity(position)

                }
                deletebtn.setOnClickListener {
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION){
                        deleteItem(itemPosition)
                    }
                }
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantites[position] > 1) {
                itemQuantites[position]--
                binding.cartItemQuantity.text = itemQuantites[position].toString()
            } else {
                cartItems.removeAt(position)
                CartitemPrice.removeAt(position)
                CartImage.removeAt(position)
                notifyItemRemoved(position)

            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantites[position] < 10) {
                itemQuantites[position]++
                binding.cartItemQuantity.text = itemQuantites[position].toString()

            }
        }

        private fun deleteItem(position: Int) {
            cartItems.removeAt(position)
            CartImage.removeAt(position)
            CartitemPrice.removeAt(position)
            notifyItemRemoved(position)
            notifyItemChanged(position, cartItems.size)

        }

    }
}