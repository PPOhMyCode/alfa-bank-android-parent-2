package com.example.alfa_bank_android_app_parent_2.ui


import android.app.AlarmManager
import android.app.AlertDialog
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
import android.content.DialogInterface
import android.content.Intent


class ParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeNavigation()
        initializeButtonNav()

    }

    override fun onBackPressed() {
        with(binding.drawerLayout)
        {
            if (isOpen) {
                closeDrawerLayout(null)
            } else {
                confirmExit { finish() }
            }
        }
    }

    private fun confirmExit(funAfterConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("School food")
        builder.setMessage("Вы точно хотите выйти?")
        builder.setCancelable(true)
        builder.setPositiveButton("Нет"
        ) { _, _ -> }
        builder.setNegativeButton("Да"){_,_->funAfterConfirm.invoke()}
        builder.show()
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
                    binding.appBarMain.toolbarTitle.text = "Мои дети"
                    true
                }
                R.id.notification -> {
                    goToFragment(NotificationFragment())
                    binding.appBarMain.toolbarTitle.text = "Уведомления"
                    true
                }
                R.id.settings -> {
                    goToFragment(SettingsFragment())
                    binding.appBarMain.toolbarTitle.text = "Настройки"
                    true
                }
                R.id.exit -> {
                    val intent = Intent(this,AuthorizationActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> {true}
            }
        }

    }

    private fun closeDrawerLayout(fragment: Fragment?) {
        GlobalScope.launch(context = Dispatchers.Main) {
            if (fragment != NotificationFragment())
                delay(20)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, fragment)
            .commit()
        closeDrawerLayout(fragment)
    }
}