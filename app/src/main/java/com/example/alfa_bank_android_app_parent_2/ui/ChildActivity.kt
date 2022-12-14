package com.example.alfa_bank_android_app_parent_2.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.ActivityChildBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import com.example.alfa_bank_android_app_parent_2.ui.menu.MenuFragment

import com.example.alfa_bank_android_app_parent_2.ui.history.NutritionHistoryFragment
import com.google.android.material.datepicker.MaterialDatePicker


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class ChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChildBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initializeDrawerLayout()
        initializeButtonNav()
        initializeNavigation()
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

    private fun initializeNavigation() {
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.this_week -> {
                    goToFragment(MenuFragment())
                    true
                }
                R.id.next_week -> {
                    goToFragment(MenuFragment())
                    true

                }
                R.id.history -> {
                    goToFragment(NutritionHistoryFragment())
                    true
                }
                R.id.exit -> {
                    val intent = ParentActivity.newIntent(this)
                    this.startActivity(intent)
                    finish()
                    true
                }
                else -> {
                    lifecycleScope.launch {
                        delay(20)
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    true
                }
            }
        }
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_child, fragment)
            .commit()
        closeDrawerLayout(fragment)
    }

    private fun closeDrawerLayout(fragment: Fragment?) {
        lifecycleScope.launch(context = Dispatchers.Main) {
            delay(20)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun confirmExit(funAfterConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("School food")
        builder.setMessage("???? ?????????? ???????????? ???????????")
        builder.setCancelable(true)
        builder.setPositiveButton(
            "??????"
        ) { _, _ -> }
        builder.setNegativeButton("????") { _, _ -> funAfterConfirm.invoke() }
        builder.show()
    }

    private fun initializeButtonNav() {
        binding.appBarMain.buttonNav.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }


    private fun initializeDrawerLayout() {
        val child = intent.extras?.get(CHILD) as Child?
        child?.let {
            with(binding.navView.getHeaderView(0)) {
                findViewById<TextView>(R.id.name).text = "${it.firstName} ${it.lastName}"
                findViewById<TextView>(R.id.schoolClass).text = "${it.schoolClass} ?? ??????????"
                findViewById<TextView>(R.id.isEat).setOnClickListener {
                    val datePicker =
                        MaterialDatePicker.Builder.dateRangePicker()
                            .setTitleText("??????, ?????????? ???????????? ???? ?????????? ????????????????")
                            .setSelection(androidx.core.util.Pair(
                                MaterialDatePicker.thisMonthInUtcMilliseconds(),
                                MaterialDatePicker.todayInUtcMilliseconds()
                            ))
                            .build()

                    datePicker.show(supportFragmentManager, "Tag")
                }
            }
        }
    }

    companion object {
        private const val CHILD = "CHILD"

        fun newIntent(packageContext: android.content.Context, child: Child) =
            Intent(packageContext, ChildActivity::class.java).apply {
                putExtra(CHILD, child)
            }
    }
}