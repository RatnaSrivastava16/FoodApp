package com.example.foodapp
import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodapp.databinding.ActivitySignUpBinding
import com.example.foodapp.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String
   private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        auth=Firebase.auth
        database=Firebase.database.reference
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        binding.createBtn.setOnClickListener {
            email = binding.userEmail.text.toString().trim()
            password = binding.userPassword.text.toString().trim()
            name = binding.userName.text.toString()
            if(email.isBlank()||password.isBlank()||name.isBlank()){
                Toast.makeText(this,"Enter all the details",Toast.LENGTH_SHORT).show()
            }
            else{
                createAccount(email,password)
            }
        }

        binding.loginbtn.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        binding.googleBtn.setOnClickListener{
            val signInIntent=googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }

    }
    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
            if(result.resultCode == Activity.RESULT_OK)
            {
                val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if(task.isSuccessful)
                {
                    val account:GoogleSignInAccount?=task.result
                    val credential= GoogleAuthProvider.getCredential(account?.idToken,null)
                    auth.signInWithCredential(credential).addOnCompleteListener{
                        task->
                            if(task.isSuccessful)
                            {
                                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()
                            }
                            else{
                                Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
                            }
                    }

                }
                else{
                    Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task->
            if(task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this,"Account Creation Failed",Toast.LENGTH_SHORT).show()
                Log.e("TAG","createAccount:failure",task.exception)
            }
        }

    }

    private fun saveUserData() {
        name=binding.userName.text.toString()
        email=binding.userEmail.text.toString().trim()
        password=binding.userPassword.text.toString().trim()
        val user= UserModel(name,email,password)
        val userId=FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)

    }
}