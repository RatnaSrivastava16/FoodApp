package com.example.foodapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.BuyAgainAdapter
import com.example.foodapp.databinding.BuyAgainItemBinding
import com.example.foodapp.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHistoryBinding.inflate(layoutInflater,container,false)
        setupRecyclerView()
        return binding.root
    }
private fun setupRecyclerView(){
    val buyAgainFoodName= arrayListOf("Burger","Pizza","Pasta","Salad","French Fries","Ice Cream")
    val buyAgainFoodPrice= arrayListOf("$5.99","$6.99","$7.99","$8.99","$9.99","$5")
    val buyAgainFoodImage=arrayListOf(R.drawable.food1,R.drawable.food,R.drawable.food1,R.drawable.food,R.drawable.food1,R.drawable.food)
    buyAgainAdapter= BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgainFoodImage)
    binding.BuyAgainRecyclerView.adapter=buyAgainAdapter
    binding.BuyAgainRecyclerView.layoutManager= LinearLayoutManager(requireContext())


}
    companion object {

    }
}