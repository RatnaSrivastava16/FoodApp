package com.example.foodapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodapp.databinding.ActivityDetailsBinding
import android.content.Intent

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val foodName=intent.getStringExtra("MenuItemName")
        val foodImage=intent.getIntExtra("MenuItemImage",0)
        binding.DetailFoodName.text=foodName
        binding.DetainFoodImage.setImageResource(foodImage)
        binding.imageButton.setOnClickListener {
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}