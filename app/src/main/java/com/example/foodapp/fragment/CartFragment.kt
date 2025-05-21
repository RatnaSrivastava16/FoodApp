package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.PayOutActivity
import com.example.foodapp.adapter.CartAdapter
import com.example.foodapp.databinding.FragmentCartBinding
import com.example.foodapp.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var cartItemList: MutableList<CartItems>
    private lateinit var adapter: CartAdapter
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        retrieveCartItems()

        binding.ProceedButton.setOnClickListener {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun retrieveCartItems() {
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodRef: DatabaseReference = database.reference
            .child("user")
            .child(userId)
            .child("CartItems")

        cartItemList = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val cartItem = foodSnapshot.getValue(CartItems::class.java)
                    cartItem?.let {
                        cartItemList.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                // Log error or show user feedback
            }
        })
    }

    private fun setAdapter() {
        adapter = CartAdapter(requireContext(), cartItemList)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
    }
}
