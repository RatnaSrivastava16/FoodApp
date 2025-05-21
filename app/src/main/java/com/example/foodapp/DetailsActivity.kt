package com.example.foodapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityDetailsBinding
import com.example.foodapp.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var auth: FirebaseAuth
    private var foodName: String? = null
    private var foodImageUrl: String? = null
    private var foodPrice: String? = null
    private var foodDescription: String? = null
    private var foodIngredients: String? = null
    private var itemFound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        // Get data from intent
        foodName = intent.getStringExtra("MenuItemName")
        foodImageUrl = intent.getStringExtra("MenuItemImage")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodIngredients = intent.getStringExtra("MenuItemIngredients")

        // Set food details
        binding.DetailFoodName.text = foodName
        binding.DescriptionTextView.text = foodDescription
        binding.IngredientsTextView.text = foodIngredients

        // Load image using Glide
        if (!foodImageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(foodImageUrl)
                .into(binding.DetailFoodImage)
        } else {
            binding.DetailFoodImage.setImageResource(R.drawable.food)
        }

        // Back button
        binding.imageButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Add to cart button
        binding.addToCart.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: return
        val cartRef = database.child("user").child(userId).child("CartItems")

        cartRef.get().addOnSuccessListener { snapshot ->

            for (itemSnapshot in snapshot.children) {
                val existingItem = itemSnapshot.getValue(CartItems::class.java)
                if (existingItem != null && existingItem.foodName == foodName) {
                    // Item found: update quantity
                    val currentQuantity = existingItem.foodQuantity?.toIntOrNull() ?: 1
                    val newQuantity = (currentQuantity + 1).toString()
                    itemSnapshot.ref.child("foodQuantity").setValue(newQuantity)
                    Toast.makeText(this, "Item quantity updated", Toast.LENGTH_SHORT).show()
                    itemFound = true
                    break
                }
            }

            if (!itemFound) {
                // Item not found: add new
                val cartItem = CartItems(
                    foodName.toString(),
                    foodPrice.toString(),
                    foodDescription.toString(),
                    foodImageUrl.toString(),
                    "1",
                    foodIngredients.toString()
                )
                cartRef.push().setValue(cartItem).addOnSuccessListener {
                    Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to add item to cart", Toast.LENGTH_SHORT).show()
                }
            }

        }.addOnFailureListener {
            Toast.makeText(this, "Failed to access cart", Toast.LENGTH_SHORT).show()
        }
    }
}
