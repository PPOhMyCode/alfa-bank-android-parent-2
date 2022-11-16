package com.example.alfa_bank_android_app_parent_2.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentNotificationBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Notification
import com.example.alfa_bank_android_app_parent_2.ui.adapters.NotificationListAdapter
import com.example.alfa_bank_android_app_parent_2.ui.service.AlarmReceiver
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.sql.Time
import java.time.DayOfWeek


class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var notificationListAdapter: NotificationListAdapter

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
        initializeButton()
        initializeBottomSheet()
        initializeRecyclerView()
    }

    private fun initializeButton() {
        initializeNotificationButton()
        initializeCloseButton()
        initializeAcceptButton()
    }

    private fun initializeRecyclerView() {
        notificationListAdapter = NotificationListAdapter(viewModel.loadNotifications())
        notificationListAdapter.onPositiveItemClick = {
            onPositiveRecyclerViewItemClick(it)
        }
        notificationListAdapter.onNegativeItemClick = {
            onNegativeRecyclerViewItemClick(it)
        }
        notificationListAdapter.onSwitchClick =
            { notification, b -> notification.idNotification?.let { viewModel.changeStateOnPause(it, b) } }

        with(binding.notificationRecyclerView) {
            adapter = notificationListAdapter
            false
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            setupSwipeListener(this)
        }
    }

    private fun onSwitchClick(notification: Notification, isOnPause: Boolean) {

    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = notificationListAdapter.notifications[viewHolder.adapterPosition]
                onNegativeRecyclerViewItemClick(item)
                viewModel.deleteNotification(item)
                notificationListAdapter.notifications = viewModel.loadNotifications()
                notificationListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun onNegativeRecyclerViewItemClick(notification: Notification) {
        finishAlarmService(notification.daysOfWeek, notification.requestCode)
    }


    private fun onPositiveRecyclerViewItemClick(notification: Notification) {
        startAlarmService(
            notification.time.minutes,
            notification.time.hours,
            notification.daysOfWeek,
            notification.requestCode
        )
    }

    private fun finishAlarmService(
        daysOfWeek: List<DayOfWeek>,
        requestCode: Int
    ) {
        with(requireActivity()) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = AlarmReceiver.newIntent(this)
            for (day in daysOfWeek) {
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    getCodedRequestCode(day, requestCode),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    private fun initializeBottomSheet() {
        binding.notificationTimeTimePicker.setIs24HourView(true)
        BottomSheetBehavior.from(binding.bottomSheet)
            .addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == 4)
                        cleanCheckBox()
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
    }

    private fun initializeCloseButton() {
        binding.closeBottomSheetButton.setOnClickListener {
            hiddenBottomSheet()
            cleanCheckBox()
        }
    }

    private fun initializeAcceptButton() {
        binding.acceptBottomSheetButton.setOnClickListener {
            if (addNewNotification()) {
                hiddenBottomSheet()
                cleanCheckBox()
            }
        }
    }

    private fun cleanCheckBox() {
        with(binding) {
            checkboxMonday.isChecked = false
            checkboxTuesday.isChecked = false
            checkboxWednesday.isChecked = false
            checkboxThursday.isChecked = false
            checkboxFriday.isChecked = false
            checkboxSaturday.isChecked = false
            checkboxSunday.isChecked = false
        }
    }

    private fun addNewNotification(): Boolean {
        val daysOfWeek = getDaysOfWeekFromTimePicker()
        if (daysOfWeek.isEmpty()) {
            Toast.makeText(requireActivity(), "Пожалуйста, выберете день", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        with(binding.notificationTimeTimePicker) {
            val time = Time(
                hour,
                minute,
                0
            )
            val requestCode = viewModel.getLastRequestCode() + 1
            viewModel.addNotification(Notification(null, daysOfWeek, time, requestCode, true))
            startAlarmService(minute, hour, daysOfWeek, requestCode)
        }
        notificationListAdapter.notifications = viewModel.loadNotifications()
        notificationListAdapter.notifyItemInserted(notificationListAdapter.notifications.size - 1)
        return true
    }

    private fun getDaysOfWeekFromTimePicker(): List<DayOfWeek> {
        val daysOfWeek = mutableListOf<DayOfWeek>()
        with(binding) {
            if (checkboxMonday.isChecked)
                daysOfWeek.add(DayOfWeek.MONDAY)
            if (checkboxTuesday.isChecked)
                daysOfWeek.add(DayOfWeek.TUESDAY)
            if (checkboxWednesday.isChecked)
                daysOfWeek.add(DayOfWeek.WEDNESDAY)
            if (checkboxThursday.isChecked)
                daysOfWeek.add(DayOfWeek.THURSDAY)
            if (checkboxFriday.isChecked)
                daysOfWeek.add(DayOfWeek.FRIDAY)
            if (checkboxSaturday.isChecked)
                daysOfWeek.add(DayOfWeek.SATURDAY)
            if (checkboxSunday.isChecked)
                daysOfWeek.add(DayOfWeek.SUNDAY)
        }
        return daysOfWeek
    }

    private fun startAlarmService(
        minute: Int,
        hour: Int,
        daysOfWeek: List<DayOfWeek>,
        requestCode: Int
    ) {
        with(requireActivity()) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = AlarmReceiver.newIntent(this)
            for (day in daysOfWeek) {
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    getCodedRequestCode(day, requestCode),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val time = getTimeToData(day, hour, minute)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + time,
                    AlarmManager.INTERVAL_DAY * 7,
                    pendingIntent
                )
            }
        }
    }

    private fun getTimeToData(dayOfWeek: DayOfWeek, hour: Int, minute: Int): Long {
        val day = getDayFromDayOfWeekToCalendarDay(dayOfWeek)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, day)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val result = calendar.getTimeInMillis() - System.currentTimeMillis()
        return if (result < 0) {
            604800000 - result
        } else {
            result
        }
    }

    private fun getDayFromDayOfWeekToCalendarDay(dayOfWeek: DayOfWeek): Int {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> Calendar.MONDAY
            DayOfWeek.TUESDAY -> Calendar.TUESDAY
            DayOfWeek.WEDNESDAY -> Calendar.WEDNESDAY
            DayOfWeek.THURSDAY -> Calendar.THURSDAY
            DayOfWeek.FRIDAY -> Calendar.FRIDAY
            DayOfWeek.SATURDAY -> Calendar.SATURDAY
            DayOfWeek.SUNDAY -> Calendar.SUNDAY
        }
    }

    private fun getCodedRequestCode(dayOfWeek: DayOfWeek, requestCode: Int) =
        ((dayOfWeek.value + 1).toString() + "0" + requestCode.toString()).toInt()

    private fun hiddenBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).setState(BottomSheetBehavior.STATE_HIDDEN)
    }

    private fun initializeNotificationButton() {
        binding.addNotificationButton.setOnClickListener {
            BottomSheetBehavior.from(binding.bottomSheet)
                .setState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }

    companion object {
        fun newInstance() = NotificationFragment()

    }
}