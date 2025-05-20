package com.example.foodapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val foodName = intent.getStringExtra("MenuItemName")
        val foodImageUrl = intent.getStringExtra("MenuItemImage")
        val foodPrice = intent.getStringExtra("MenuItemPrice")
        val foodDescription = intent.getStringExtra("MenuItemDescription")
        val foodIngredients = intent.getStringExtra("MenuItemIngredients")

        // Set food name
        binding.DetailFoodName.text = foodName
        binding.DescriptionTextView.text = foodDescription
        binding.IngredientsTextView.text = foodIngredients


        // Load image using Glide
        if (!foodImageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(foodImageUrl)
                .into(binding.DetailFoodImage)
        } else {
            // Optional: fallback image if URL is null
            binding.DetailFoodImage.setImageResource(R.drawable.food)
        }

        // Handle back button
        binding.imageButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
