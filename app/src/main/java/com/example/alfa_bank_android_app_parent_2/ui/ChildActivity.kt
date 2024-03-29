package com.example.alfa_bank_android_app_parent_2.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesChildImpl
import com.example.alfa_bank_android_app_parent_2.databinding.ActivityChildBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import com.example.alfa_bank_android_app_parent_2.domain.entiies.ReceiptsItem
import com.example.alfa_bank_android_app_parent_2.ui.menu.MenuFragment

import com.example.alfa_bank_android_app_parent_2.ui.history.NutritionHistoryFragment
import com.example.alfa_bank_android_app_parent_2.ui.iseat.IsEatFragment
import com.example.alfa_bank_android_app_parent_2.ui.receipts.ReceiptsFragment
import com.example.alfa_bank_android_app_parent_2.ui.sheduler.SchedulerFragment
import com.google.android.material.datepicker.MaterialDatePicker


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class ChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChildBinding

    private var type:Boolean = true

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
                R.id.timetable -> {
                    binding.appBarMain.copyImageButton.visibility = View.GONE
                    binding.appBarMain.secondTextView.visibility = View.GONE
                    binding.appBarMain.firstTextView.text = "Расписание"
                    goToFragment(SchedulerFragment.newInstance())
                    true
                }

                R.id.this_week -> {
                    binding.appBarMain.copyImageButton.visibility = View.VISIBLE
                    binding.appBarMain.secondTextView.visibility = View.VISIBLE
                    binding.appBarMain.firstTextView.text = "Меню"
                    binding.appBarMain.secondTextView.text = "Питание на неделю"
                    goToFragment(MenuFragment.newInstance(MenuFragment.CHOOSE_MENU_MODE))
                    true
                }
                R.id.next_week -> {
                    binding.appBarMain.copyImageButton.visibility = View.VISIBLE
                    binding.appBarMain.secondTextView.visibility = View.VISIBLE
                    binding.appBarMain.firstTextView.text = "Меню"
                    binding.appBarMain.secondTextView.text = "Закакз питания на неделю"
                    goToFragment(MenuFragment.newInstance(MenuFragment.LOAD_MENU_MODE))
                    true

                }
                R.id.history -> {
                    binding.appBarMain.copyImageButton.visibility = View.GONE
                    binding.appBarMain.secondTextView.visibility = View.GONE
                    binding.appBarMain.firstTextView.text = "История питания"
                    goToFragment(NutritionHistoryFragment())
                    true
                }
                R.id.nav_receipts -> {
                    binding.appBarMain.copyImageButton.visibility = View.GONE
                    binding.appBarMain.secondTextView.visibility = View.GONE
                    binding.appBarMain.firstTextView.text = "Квитанции"
                    goToFragment(ReceiptsFragment())
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

    fun closeDrawer(receiptsItem:ReceiptsItem) {
        type = false
        binding.appBarMain.firstTextView.text = (receiptsItem.year+" "+receiptsItem.month).replace("[","").replace("]","").replace("10","").replace(" ","").replace(".","").replace(",","").replace("0","")
        binding.appBarMain.buttonNav.setImageResource(R.drawable.ic_baseline_navigate_before_24)
        lifecycleScope.launch(context = Dispatchers.Main) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    fun closeAppBarMain(){
        binding.appBarMain.topAppBar.visibility = View.GONE
    }

    fun openAppBarMain(){
        binding.appBarMain.topAppBar.visibility = View.VISIBLE
    }


    private fun confirmExit(funAfterConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("School food")
        builder.setMessage("Вы точно хотите выйти?")
        builder.setCancelable(true)
        builder.setPositiveButton(
            "Нет"
        ) { _, _ -> }
        builder.setNegativeButton("Да") { _, _ -> funAfterConfirm.invoke() }
        builder.show()
    }

    private fun initializeButtonNav() {
        binding.appBarMain.buttonNav.setOnClickListener {
            if(!type) {
                type = true
                binding.appBarMain.copyImageButton.visibility = View.GONE
                binding.appBarMain.secondTextView.visibility = View.GONE
                binding.appBarMain.firstTextView.text = "Квитанции"
                binding.appBarMain.buttonNav.setImageResource(R.drawable.ic_list)
                goToFragment(ReceiptsFragment())
            }else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }


    private fun initializeDrawerLayout() {
        val child = intent.extras?.get(CHILD) as Child?
        child?.let {
            PreferencesChildImpl(this).idChild = child.id
            Log.d("asdasd",child.schoolClass)
            PreferencesChildImpl(this).schoolClass = child.schoolClass

            with(binding.navView.getHeaderView(0)) {
                findViewById<TextView>(R.id.name).text = "${it.firstName} ${it.lastName}"
                findViewById<TextView>(R.id.schoolClass).text = it.schoolClass

                findViewById<ImageView>(R.id.dishImageView).setOnClickListener {

                }

                findViewById<TextView>(R.id.isEat).setOnClickListener {

                    goToFragment(IsEatFragment.newInstance())

                    binding.appBarMain.copyImageButton.visibility = View.GONE
                    binding.appBarMain.secondTextView.visibility = View.GONE
                    binding.appBarMain.firstTextView.text = "Питание"


                    /**
                    val datePicker =
                        MaterialDatePicker.Builder.dateRangePicker()
                            .setTitleText("Дни, когда ребенк не будет питаться")
                            .setSelection(
                                androidx.core.util.Pair(
                                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                                    MaterialDatePicker.todayInUtcMilliseconds()
                                )
                            )
                            .build()

                    datePicker.show(supportFragmentManager, "Tag")
                    */
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