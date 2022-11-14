package com.example.alfa_bank_android_app_parent_2.ui.createnotification

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alfa_bank_android_app_parent_2.R

class CreateNotificationFragment : Fragment() {

    companion object {
        fun newInstance() = CreateNotificationFragment()
    }

    private lateinit var viewModel: CreateNotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_notification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateNotificationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}