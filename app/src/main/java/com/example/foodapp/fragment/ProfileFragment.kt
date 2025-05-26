package com.example.foodapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentProfileBinding
import com.example.foodapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val auth=FirebaseAuth.getInstance()
    private val database= FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        setUserData()
        binding.saveInfoBtn.setOnClickListener {
            val name=binding.UserName.text.toString()
            val address=binding.UserAddress.text.toString()
            val email=binding.UserEmail.text.toString()
            val phone=binding.UserPhone.text.toString()
            updateUserData(name,address,email,phone)

        }
        return binding.root
    }
    private fun updateUserData(name:String,address:String,email:String,phone:String){
        val userId=auth.currentUser?.uid
        if(userId!=null){
            val userReference=database.getReference("user").child(userId)
            userReference.child("name").setValue(name)
            userReference.child("address").setValue(address)
            userReference.child("email").setValue(email)
            userReference.child("phone").setValue(phone)
            Toast.makeText(requireContext(),"Profile Updated",Toast.LENGTH_SHORT).show()
        }
    }
    private fun setUserData() {
        val userId=auth.currentUser?.uid
        if(userId!=null){
            val userReference=database.getReference("user").child(userId)
            userReference.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val userProfile=snapshot.getValue(UserModel::class.java)
                        if(userProfile!=null){
                            binding.UserName.setText(userProfile.name)
                            binding.UserAddress.setText(userProfile.address)
                            binding.UserEmail.setText(userProfile.email)
                            binding.UserPhone.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    companion object {

    }
}