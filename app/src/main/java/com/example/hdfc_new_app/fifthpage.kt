package com.example.hdfc_new_app

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.hdfc_new_app.databinding.ActivityFifthpageBinding

class fifthpage : AppCompatActivity() {

    private lateinit var binding : ActivityFifthpageBinding
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFifthpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCountdownTimer()

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
