package com.example.alfa_bank_android_app_parent_2.ui


import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.ActivityParentBinding
import com.example.alfa_bank_android_app_parent_2.ui.children.ChildrenFragment
import com.example.alfa_bank_android_app_parent_2.ui.notification.NotificationFragment
import com.example.alfa_bank_android_app_parent_2.ui.service.AlarmReceiver
import com.example.alfa_bank_android_app_parent_2.ui.settings.SettingsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeNavigation()
        initializeButtonNav()
        startAlarmService()
    }

    override fun onBackPressed() {
        with(binding.drawerLayout)
        {
            if (isOpen) {
                closeDrawerLayout()
            } else {
                finish()
            }
        }
    }

    private fun initializeButtonNav() {
        binding.appBarMain.buttonNav.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun initializeNavigation() {
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.children -> {
                    goToFragment(ChildrenFragment())
                    //binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.notification -> {
                    goToFragment(NotificationFragment())
                    true
                }
                R.id.settings -> {
                    goToFragment(SettingsFragment())
                    true
                }
                else -> {
                    true
                }
            }
        }

    }

    private fun closeDrawerLayout() {
        GlobalScope.launch(context = Dispatchers.Main) {
            delay(20)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, fragment)
            .commit()
        closeDrawerLayout()
    }

    private fun startAlarmService() {
        Log.d("alarm","parentStart")
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val calendar = java.util.Calendar.getInstance()
        calendar.add(java.util.Calendar.MONDAY, 8)
        calendar.add(java.util.Calendar.MONDAY, 8)
        calendar.add(java.util.Calendar.MONDAY, 8)
        calendar.add(java.util.Calendar.MONDAY, 8)


        calendar.add(java.util.Calendar.AM_PM, 21)
        val intent = AlarmReceiver.newIntent(this)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            100,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}