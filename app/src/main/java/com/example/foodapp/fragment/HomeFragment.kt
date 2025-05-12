package com.example.foodapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodapp.MenuBottomSheetFragment
import com.example.foodapp.R
import com.example.foodapp.adapter.PopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        binding.ViewMenuBtn.setOnClickListener {
            val bottomSheetDialog= MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var imageList=ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner4, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner4, ScaleTypes.FIT))
        var imageSlider=binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)
        imageSlider.setItemClickListener(object:ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition=imageList[position]
                val itemMessage="Selected Image $position"
                Toast.makeText(requireContext(),itemMessage, Toast.LENGTH_SHORT).show()
            }
        })
        val foodName=listOf("Pizza","Burger","Hotdog","Pizza","Burger","Hotdog")
        val Price=listOf("$5","$2","$1","$5","$2","$1")
        val popularFoodImages=listOf(R.drawable.food1,R.drawable.food,R.drawable.food1,R.drawable.food,R.drawable.food1,R.drawable.food)
        val adapter= PopularAdapter(foodName,Price,popularFoodImages)
        binding.PopularRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter=adapter



    }
    companion object {

    }
}