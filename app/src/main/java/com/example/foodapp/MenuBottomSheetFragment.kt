package com.example.foodapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.adapter.MenuAdapter
import com.example.foodapp.databinding.FragmentMenuBottomSheetBinding
import com.example.foodapp.model.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.*

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        binding.buttonback.setOnClickListener {
            dismiss()
        }

        retrieveMenuItems()

        return binding.root
    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                if (menuItems.isEmpty()) {
                    Toast.makeText(requireContext(), "No menu items available", Toast.LENGTH_SHORT).show()
                } else {
                    setAdapter()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load menu: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setAdapter() {
        val adapter = MenuAdapter(menuItems, requireContext())
        binding.menuRecylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecylerView.adapter = adapter
    }

    companion object {
        // You can add factory methods or constants here if needed
    }
}
