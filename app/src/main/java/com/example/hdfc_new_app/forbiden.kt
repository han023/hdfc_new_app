package com.example.hdfc_new_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hdfc_new_app.databinding.ActivityForbidenBinding

class forbiden : AppCompatActivity() {

    private lateinit var binding:ActivityForbidenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForbidenBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}