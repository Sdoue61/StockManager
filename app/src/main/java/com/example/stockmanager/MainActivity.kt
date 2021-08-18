package com.example.stockmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
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

        val signupnow = findViewById<Button>(R.id.BTsignup)
        signupnow.setOnClickListener {
            Toast.makeText(this,"You are now registered", Toast.LENGTH_LONG).show()
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
                    val intent= Intent(this,MainActivity2::class.java)
                    startActivity(intent)
                return true
            }
        }
        return true//super.onOptionsItemSelected(item)
    }

}