package com.example.foodapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.PayOutActivity
import com.example.foodapp.R
import com.example.foodapp.adapter.CartAdapter
import com.example.foodapp.databinding.FragmentCartBinding
import android.content.Intent


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCartBinding.inflate(inflater,container,false)
        val cartFoodName=listOf("Burger","Pizza","Pasta","Salad","French Fries","Ice Cream")
        val cartItemPrice=listOf("$5.99","$6.99","$7.99","$8.99","$9.99","$5")
        val cartImage=listOf(R.drawable.food1,R.drawable.food,R.drawable.food1,R.drawable.food,R.drawable.food1,R.drawable.food)
        val adapter= CartAdapter(ArrayList(cartFoodName),ArrayList(cartItemPrice),ArrayList(cartImage))
        binding.cartRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter=adapter
        binding.ProceedButton.setOnClickListener {
            val intent= Intent(requireContext(), PayOutActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {
    }
}