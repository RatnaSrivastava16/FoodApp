package com.example.foodapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodapp.databinding.ActivityPayOutBinding

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPayOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.placeOrderBtn.setOnClickListener {
            val bottomSheetDialog=CongratsBottomSheetFragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")
        }

    }
}