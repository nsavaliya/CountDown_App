package com.example.countdownapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var countDownTimer: CountDownTimer
    lateinit var stopwatch : Chronometer
    var running = false
    var offset: Long = 0
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    private val COUNTDOWN_INTERVAL: Long = 1000
    val BASE_KEY = "base"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        stopwatch = findViewById<Chronometer>(R.id.stopwatch)
        if(savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if(running){
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }else{
                setBaseTime()
            }
        }
        var  Second_five_pluse=findViewById<Button>(R.id.fiveplus)
        var initName=findViewById<EditText>(R.id.number)
        Second_five_pluse.setOnClickListener {
            offset = offset+5000
            updateTimeRemaining()
        }
        var  ten_plus=findViewById<Button>(R.id.tenplus)

        ten_plus.setOnClickListener {
            offset = offset+10000
            updateTimeRemaining()
        }

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            if (initName.text.toString().isNotEmpty()) {
                offset = initName.text.toString().toLong() * 1_000
            }

            countDownTimer = object : CountDownTimer(offset, COUNTDOWN_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    offset = millisUntilFinished
                    updateTimeRemaining()
                }

                override fun onFinish() {
                    stopwatch.text = "0"
                }
            }.start()

        }
        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener {
            stopCountdown()
        }


        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    private fun updateTimeRemaining() {
        val minutes = (offset / 1_000) / 60
        val seconds = (offset / 1_000) % 60
        stopwatch.text = String.format("%02d:%02d", minutes, seconds)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(OFFSET_KEY,offset)
        outState.putBoolean(RUNNING_KEY,running)
        outState.putLong(BASE_KEY,stopwatch.base)
        super.onSaveInstanceState(outState)
    }
    private fun stopCountdown() {
        countDownTimer.cancel()
    }

    fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }
    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }
}