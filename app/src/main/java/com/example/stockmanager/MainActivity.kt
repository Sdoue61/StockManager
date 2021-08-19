package com.example.stockmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stockmanager.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        val registernow = findViewById<Button>(R.id.BTregister)
        registernow.setOnClickListener {
            binding.LLlogin.visibility = View.GONE
            binding.LLregister.visibility = View.VISIBLE
        }

        val registeryourmail = findViewById<EditText>(R.id.ETregistermail)
        val registerpwd1 = findViewById<EditText>(R.id.ETregisterpwd1)
        val registerpwd2 = findViewById<EditText>(R.id.ETregisterpwd2)
        val signupnow = findViewById<Button>(R.id.BTsignup)
        signupnow.setOnClickListener {
            Toast.makeText(this,"You are now registered", Toast.LENGTH_LONG).show()
        }

        auth = Firebase.auth
        signupnow.setOnClickListener {
            val email  = registeryourmail.text.toString()
            val password = registerpwd1.text.toString()
            val password2 = registerpwd2.text.toString()
            if (password != password2) //and ()
            {
                Toast.makeText(
                    baseContext, "Passwords aren't identical",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password.length < 5) {
                Toast.makeText(
                    baseContext, "You are missing something",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            baseContext,
                                            "A verification mail has been to you",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.LLregister.visibility = View.GONE
                                        binding.LLlogin.visibility = View.VISIBLE
                                    }
                                }
                        } else {
                            Toast.makeText(
                                baseContext, "Sign up failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            binding.LLregister.visibility = View.GONE
            binding.LLlogin.visibility = View.VISIBLE
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true//super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> Toast.makeText(this, "You've been logged out", Toast.LENGTH_SHORT).show()
            R.id.nav_profile -> {
                    val intent= Intent(this,ProfilePage::class.java)
                    startActivity(intent)
                return true
            }
        }
        return true//super.onOptionsItemSelected(item)
    }

}