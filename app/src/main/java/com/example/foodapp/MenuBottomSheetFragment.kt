package com.example.foodapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.adapter.MenuAdapter
import com.example.foodapp.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)
        binding.buttonback.setOnClickListener{
            dismiss()
        }
        val menuFoodName=listOf("Burger","Pizza","Pasta","Salad","French Fries","Ice Cream","Pizza","Pasta","Salad","French Fries","Ice Cream")
        val menuItemPrice=listOf("$5.99","$6.99","$7.99","$8.99","$9.99","$5","$6.99","$7.99","$8.99","$9.99","$5")
        val  menuImage=listOf(R.drawable.food1,R.drawable.food,R.drawable.food1,R.drawable.food,R.drawable.food1,R.drawable.food,R.drawable.food,R.drawable.food1,R.drawable.food,R.drawable.food1,R.drawable.food)
        val adapter= MenuAdapter(ArrayList(menuFoodName),ArrayList(menuItemPrice),ArrayList(menuImage),requireContext())
        binding.menuRecylerView.layoutManager= LinearLayoutManager(requireContext())
        binding.menuRecylerView.adapter=adapter
        return binding.root
    }

    companion object {

    }
}