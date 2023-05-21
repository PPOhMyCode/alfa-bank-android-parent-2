package com.example.alfa_bank_android_app_parent_2.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesUserImpl
import com.example.alfa_bank_android_app_parent_2.databinding.ActivityParentBinding
import com.example.alfa_bank_android_app_parent_2.ui.children.ChildrenFragment
import com.example.alfa_bank_android_app_parent_2.ui.notification.NotificationFragment
import com.example.alfa_bank_android_app_parent_2.ui.settings.SettingsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


class ParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParentBinding
    private lateinit var filePhoto: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeNavigation()
        initializeButtonNav()
        filePhoto = getPhotoFile("photo.jpg")
        initializeAvatarClickListener()
        initializeName()
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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        if (resultCode == 1001) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                loadPhotosFromGallery()
            } else {
                chooseImageGallery()
            }
        }

        if (requestCode == 1) {
            val headerView = binding.navView.getHeaderView(0)
            headerView?.findViewById<ImageView>(R.id.dishImageView)
                ?.setImageBitmap(data.extras?.get("data") as Bitmap?)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun initializeName() {
        val preferencesUser = PreferencesUserImpl(this)
        preferencesUser.user?.let {
            val headerView = binding.navView.getHeaderView(0)


            headerView?.findViewById<TextView>(R.id.firstName)?.text = it.firstName
            headerView?.findViewById<TextView>(R.id.lastName)?.text = it.lastName
        }
    }

    private fun initializeAvatarClickListener() {
        val headerView = binding.navView.getHeaderView(0)
        headerView?.findViewById<ImageView>(R.id.dishImageView)?.setOnClickListener {
            loadPhotosFromGallery()
        }
    }

    private fun loadPhotosFromGallery() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 1001)
        } else {
            makePhoto()
            //chooseImageGallery();
        }
    }


    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    private fun makePhoto() {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePhotoIntent, 1)
    }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
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
                    val intent = Intent(this, AuthorizationActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> {
                    true
                }
            }
        }

    }

    private fun closeDrawerLayout(fragment: Fragment?) {
        lifecycleScope.launch(context = Dispatchers.Main) {
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

    companion object {
        fun newIntent(packageContext: Context): Intent =
            Intent(packageContext, ParentActivity::class.java)
    }
}