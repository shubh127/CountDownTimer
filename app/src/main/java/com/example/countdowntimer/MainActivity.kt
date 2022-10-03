package com.example.countdowntimer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var btnDatePicker: Button
    private lateinit var tvTimeRemaining: TextView
    private var date: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        configViews()
    }

    private fun initViews() {
        btnDatePicker = findViewById(R.id.btn_date_picker)
        tvTimeRemaining = findViewById(R.id.tv_time_remaining)
    }

    private fun configViews() {
        btnDatePicker.setOnClickListener { showDateTimePicker() }
    }


    private fun showDateTimePicker() {
        val currentDate: Calendar = Calendar.getInstance()
        date = Calendar.getInstance()
        val dateDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)
                val timeDialog = TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        date.set(Calendar.MINUTE, minute)

                        onDateTimeSelected(date)
                    },
                    currentDate.get(Calendar.HOUR_OF_DAY),
                    currentDate.get(Calendar.MINUTE),
                    false
                )

                timeDialog.show()
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DATE)
        )
        dateDialog.datePicker.minDate = date.timeInMillis
        dateDialog.show()
    }

    private fun onDateTimeSelected(date: Calendar) {
        var startTime = System.currentTimeMillis()


        val mCountDownTimer = object : CountDownTimer(date.timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var text = ""
                startTime -= 1
                val serverUptimeSeconds: Long = (millisUntilFinished - startTime) / 1000
                val daysLeft = String.format("%d", serverUptimeSeconds / 86400)
                text += "$daysLeft-day(s) \n"
                val hoursLeft = String.format("%d", serverUptimeSeconds % 86400 / 3600)
                text += "$hoursLeft-hours\n"
                val minutesLeft = String.format("%d", serverUptimeSeconds % 86400 % 3600 / 60)
                text += "$minutesLeft-minutes\n"
                val secondsLeft = String.format("%d", serverUptimeSeconds % 86400 % 3600 % 60)
                text += "$secondsLeft-seconds\n"
                tvTimeRemaining.text = text
            }

            override fun onFinish() {}
        }.start()
    }

}