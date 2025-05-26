package com.example.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.CartItemBinding
import com.example.foodapp.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartAdapter(
    private val context: Context,
    private val cartItems: MutableList<CartItems>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val userId = auth.currentUser?.uid ?: ""
    private val cartItemsReference: DatabaseReference =
        database.reference.child("user").child(userId).child("CartItems")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size
    fun getUpdatedItemQuantities(): MutableList<Int> {
        val itemQuantities = mutableListOf<Int>()
        for (item in cartItems) {
            val quantity = item.foodQuantity?.toIntOrNull() ?: 1
            itemQuantities.add(quantity)
        }
        return itemQuantities
    }


    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = cartItems[position]
            val quantity = item.foodQuantity?.toIntOrNull() ?: 1
            binding.cartFoodName.text = item.foodName
            binding.cartItemPrice.text = item.foodPrice
            binding.cartItemQuantity.text = quantity.toString()

            Glide.with(context).load(item.foodImage).into(binding.cartImage)

            binding.plusbn.setOnClickListener {
                updateQuantity(position, quantity + 1)
            }

            binding.minusbtn.setOnClickListener {
                if (quantity > 1) {
                    updateQuantity(position, quantity - 1)
                } else {
                    deleteItem(position)
                }
            }

            binding.deletebtn.setOnClickListener {
                deleteItem(position)
            }
        }

        private fun updateQuantity(position: Int, newQuantity: Int) {
            getUniqueKeyAtPosition(position) { key ->
                key?.let {
                    cartItemsReference.child(it).child("foodQuantity")
                        .setValue(newQuantity.toString())
                        .addOnSuccessListener {
                            cartItems[position].foodQuantity = newQuantity.toString()
                            notifyItemChanged(position)
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to update quantity", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        private fun deleteItem(position: Int) {
            getUniqueKeyAtPosition(position) { key ->
                key?.let {
                    cartItemsReference.child(it).removeValue()
                        .addOnSuccessListener {
                            cartItems.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, cartItems.size)
                            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        private fun getUniqueKeyAtPosition(position: Int, onComplete: (String?) -> Unit) {
            cartItemsReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var key: String? = null
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == position) {
                            key = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(key)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error fetching key", Toast.LENGTH_SHORT).show()
                    onComplete(null)
                }
            })
        }
    }

    companion object {

    }
}
