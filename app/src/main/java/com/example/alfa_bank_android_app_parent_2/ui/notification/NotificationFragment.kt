package com.example.alfa_bank_android_app_parent_2.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.icu.util.Calendar
import android.icu.util.LocaleData
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentNotificationBinding
import com.example.alfa_bank_android_app_parent_2.ui.authentication.AuthenticationViewModel
import com.example.alfa_bank_android_app_parent_2.ui.service.AlarmReceiver
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.sql.Time
import java.time.LocalDateTime
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

class NotificationFragment : Fragment() {
    //private lateinit var viewModel: NotificationViewModel
    private lateinit var binding: FragmentNotificationBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[NotificationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeClickListener()
        binding.chooseTimeButton.text =
            "Время уведомления " + viewModel.preferences.timePicked.time.toString()
        initializeCheckBox()
    }

    private fun initializeClickListener() {
        val picker = getPicker()
        binding.chooseTimeButton.setOnClickListener {
            picker.show(childFragmentManager, "ChooseAlarmTime")
        }
    }

    private fun initializeCheckBox() {
        with(viewModel.preferences.timePicked) {
            binding.checkboxMonday.isChecked = monday
            binding.checkboxTuesday.isChecked = tuesday
            binding.checkboxWednesday.isChecked = wednesday
            binding.checkboxThursday.isChecked = thursday
            binding.checkboxFriday.isChecked = friday
            binding.checkboxSaturday.isChecked = saturday
            binding.checkboxSunday.isChecked = sunday
        }
        initializeCheckBoxChangeState()
    }

    private fun initializeCheckBoxChangeState() {
        with(viewModel.preferences) {
            binding.checkboxMonday.setOnClickListener {
                val time = timePicked
                time.monday = binding.checkboxMonday.isChecked
                timePicked = time
                startAlarmService()
            }
            binding.checkboxTuesday.setOnClickListener {
                val time = timePicked
                time.tuesday = binding.checkboxTuesday.isChecked
                timePicked = time
                startAlarmService()
            }
            binding.checkboxWednesday.setOnClickListener {
                val time = timePicked
                time.wednesday = binding.checkboxWednesday.isChecked
                timePicked = time
                startAlarmService()
            }
            binding.checkboxThursday.setOnClickListener {
                val time = timePicked
                time.thursday = binding.checkboxThursday.isChecked
                timePicked = time
                startAlarmService()
            }
            binding.checkboxFriday.setOnClickListener {
                val time = timePicked
                time.friday = binding.checkboxFriday.isChecked
                timePicked = time
                startAlarmService()
            }
            binding.checkboxSaturday.setOnClickListener {
                val time = timePicked
                time.saturday = binding.checkboxSaturday.isChecked
                timePicked = time
                startAlarmService()
            }
            binding.checkboxSunday.setOnClickListener {
                val time = timePicked
                time.sunday = binding.checkboxSunday.isChecked
                timePicked = time
                startAlarmService()
            }
        }
    }

    private fun startAlarmService() {
        Log.d("alarm","startNotification")
        with(requireActivity()) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val calendar = java.util.Calendar.getInstance()
            calendar.add(java.util.Calendar.SECOND, 1)
            val intent = AlarmReceiver.newIntent(this)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                100,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),10,pendingIntent)
            //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        }
    }

    private fun getPicker(): MaterialTimePicker {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(viewModel.preferences.timePicked.time.hours)
                .setMinute(viewModel.preferences.timePicked.time.minutes)
                .build()
        with(picker) {
            addOnPositiveButtonClickListener {
                val time = viewModel.preferences.timePicked
                time.time = Time(hour, minute, 0)
                viewModel.preferences.timePicked = time
                binding.chooseTimeButton.text =
                    "Время уведомления " + viewModel.preferences.timePicked.time.toString()
            }
        }
        return picker
    }

    companion object {
        fun newInstance() = NotificationFragment()
    }

}