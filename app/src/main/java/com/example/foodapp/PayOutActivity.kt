package com.example.foodapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.databinding.ActivityPayOutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PayOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayOutBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var foodNames: ArrayList<String>
    private lateinit var foodPrices: ArrayList<String>
    private lateinit var foodDescriptions: ArrayList<String>
    private lateinit var foodImages: ArrayList<String>
    private lateinit var foodIngredients: ArrayList<String>
    private lateinit var foodQuantities: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        getCartIntentData()
        setUserData()
        displayTotalAmount()

        binding.placeOrderBtn.setOnClickListener {
            placeOrder()
        }
    }

    private fun getCartIntentData() {
        foodNames = intent.getStringArrayListExtra("foodNames") ?: ArrayList()
        foodPrices = intent.getStringArrayListExtra("foodPrices") ?: ArrayList()
        foodDescriptions = intent.getStringArrayListExtra("foodDescriptions") ?: ArrayList()
        foodImages = intent.getStringArrayListExtra("foodImages") ?: ArrayList()
        foodIngredients = intent.getStringArrayListExtra("foodIngredients") ?: ArrayList()
        foodQuantities = intent.getIntegerArrayListExtra("foodQuantities") ?: ArrayList()
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userReference = databaseReference.child("user").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java)
                        val address = snapshot.child("address").getValue(String::class.java)
                        val phone = snapshot.child("phone").getValue(String::class.java)
                        binding.apply {
                            PayEditName.setText(name ?: "")
                            PayEditAddress.setText(address ?: "")
                            PayEditPhone.setText(phone ?: "")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }

    private fun calculateTotalPrice(): Int {
        var total = 0
        for (i in foodPrices.indices) {
            val price = foodPrices[i].toIntOrNull() ?: 0
            val quantity = foodQuantities.getOrNull(i) ?: 1
            total += price * quantity
        }
        return total
    }

    private fun displayTotalAmount() {
        val totalPrice = calculateTotalPrice()
        binding.AmountEdit.setText(totalPrice.toString())
    }

    private fun placeOrder() {
        val orderMap = mutableMapOf<String, Any>()
        orderMap["name"] = binding.PayEditName.text.toString()
        orderMap["address"] = binding.PayEditAddress.text.toString()
        orderMap["phone"] = binding.PayEditPhone.text.toString()

        val userId = auth.currentUser?.uid ?: return
        val orderId = databaseReference.child("orders").push().key ?: "order_${System.currentTimeMillis()}"

        databaseReference.child("orders").child(orderId).setValue(orderMap)
            .addOnSuccessListener {
                val bottomSheetDialog = CongratsBottomSheetFragment()
                bottomSheetDialog.show(supportFragmentManager, "Test")
            }
            .addOnFailureListener {
                // Show error to user
            }
    }
}
