package com.example.teran.ui.meditation

import android.app.Dialog
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.teran.R
import com.example.teran.databinding.ActivityMeditationBinding

class MeditationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditationBinding
    private var timeSelected: Int = 0
    private var timeCountdown: CountDownTimer? = null
    private var timeProgress = 0
    private var pauseOffset: Long = 0
    private var isStart = true
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0.0f
            title = "Meditation"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.addMeditateBtn.setOnClickListener {
            setTimer()
        }

        binding.startBtn.setOnClickListener {
            startTimerSetup()
        }

        binding.resetProfileBtn.setOnClickListener {
            resetTime()
        }

        binding.addTime.setOnClickListener {
            addExtraTime()
        }
    }

    private fun addExtraTime() {
        val progressBar = binding.progressBar
        if (timeSelected != 0) {
            timeSelected += 15
            progressBar.max = timeSelected
            timerPause()
            startTimer(pauseOffset)
            Toast.makeText(this, "15 sec added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun timerPause() {
        if (timeCountdown != null) {
            timeCountdown!!.cancel()
        }
        stopMusic()
    }

    private fun resetTime() {
        if (timeCountdown != null) {
            timeCountdown!!.cancel()
            timeProgress = 0
            pauseOffset = 0
            timeCountdown = null
            val startBtn = binding.startBtn
            startBtn.text = getString(R.string.start)
            isStart = true
            val progressBar = binding.progressBar
            progressBar.progress = 0
            val timeLeft = binding.timerLeft
            timeLeft.text = "0"
        }
        stopMusic()
    }

    private fun setTimer() {
        val timeDialog = Dialog(this)
        timeDialog.setContentView(R.layout.add_timer_dialog)
        val timeSet = timeDialog.findViewById<EditText>(R.id.edit_dialog)
        val timeLeft = binding.timerLeft
        val startButton = binding.startBtn
        val progressBar = binding.progressBar
        val setTimerButton = timeDialog.findViewById<Button>(R.id.setTimerBtn)

        setTimerButton.setOnClickListener {
            if (timeSet.text.isEmpty()) {
                Toast.makeText(this, "Please, enter time duration", Toast.LENGTH_SHORT).show()
            } else {
                resetTime()
                timeLeft.text = timeSet.text
                startButton.text = getString(R.string.start)
                timeSelected = timeSet.text.toString().toInt()
                progressBar.max = timeSelected
            }
            timeDialog.dismiss()
        }
        timeDialog.show()
    }

    private fun startTimerSetup() {
        val startBtn = binding.startBtn
        if (timeSelected > timeProgress) {
            if (isStart) {
                startBtn.text = getString(R.string.pause)
                startTimer(pauseOffset)
                isStart = false
            } else {
                isStart = true
                startBtn.text = getString(R.string.resume)
                timerPause()
            }

        } else {
            Toast.makeText(this, "Enter time", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTimer(pauseOffSetL: Long) {
        val progressBar = binding.progressBar
        progressBar.progress = timeProgress
        timeCountdown = object : CountDownTimer(
            (timeSelected * 1000).toLong() - pauseOffSetL * 1000, 1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                timeProgress++
                pauseOffset = timeSelected.toLong() - millisUntilFinished / 1000
                progressBar.progress = timeSelected - timeProgress
                val timeLeft = binding.timerLeft
                timeLeft.text = (timeSelected - timeProgress).toString()
            }

            override fun onFinish() {
                resetTime()
                Toast.makeText(
                    this@MeditationActivity, "Time's up!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }.start()
        playMusic()
    }

    private fun playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.calming_music)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timeCountdown != null) {
            timeCountdown?.cancel()
            timeProgress = 0
        }
        stopMusic()
    }
}
