package com.example.foodapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.MenuAdapter
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.model.MenuItem
import com.google.firebase.database.*

class SearchFragment : Fragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var fullMenuItems: MutableList<MenuItem> // Full list from Firebase
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        retrieveMenuItems()
        setupSearchView()

        return binding.root
    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        fullMenuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        fullMenuItems.add(it)
                    }
                }

                if (fullMenuItems.isEmpty()) {
                    Toast.makeText(requireContext(), "No menu items available", Toast.LENGTH_SHORT).show()
                } else {
                    setAdapter(fullMenuItems)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load menu: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setAdapter(menuList: List<MenuItem>) {
        adapter = MenuAdapter(menuList, requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterMenuItems(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMenuItems(newText.orEmpty())
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        val filteredList = fullMenuItems.filter {
            it.name.orEmpty().contains(query, ignoreCase = true)
        }

        setAdapter(filteredList)
    }
}
