package com.example.foodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.adapter.NotificationAdapter
import com.example.foodapp.databinding.FragmentNotificationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentNotificationBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentNotificationBottomSheetBinding.inflate(layoutInflater,container,false)
        var notification=listOf("Your Order has been Cancelled","Your Order has been Cancelled","Your Order has been Cancelled")
        var notificationImage=listOf(R.drawable.food,R.drawable.food1,R.drawable.food)
        val adapter= NotificationAdapter(
            ArrayList(notification),
            ArrayList(notificationImage)
        )

        binding.notificationRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter=adapter
        return binding.root
    }

    companion object {
    }
}