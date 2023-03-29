package com.example.alfa_bank_android_app_parent_2.ui.sheduler

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alfa_bank_android_app_parent_2.R

class SchedulerFragment : Fragment() {

    companion object {
        fun newInstance() = SchedulerFragment()
    }

    private lateinit var viewModel: SchedulerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scheduler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SchedulerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}