package com.example.foodapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodapp.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding:ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val locationList= arrayOf("Jaipur","Odisha","Bundi","Bikaner","Jodhpur","Kota","Ajmer")
        val adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView: AutoCompleteTextView =binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)


    }
}