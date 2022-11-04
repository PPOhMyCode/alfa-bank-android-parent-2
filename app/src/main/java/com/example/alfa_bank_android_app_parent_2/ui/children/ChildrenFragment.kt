package com.example.alfa_bank_android_app_parent_2.ui.children

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alfa_bank_android_app_parent_2.R

class ChildrenFragment : Fragment() {

    companion object {
        fun newInstance() = ChildrenFragment()
    }

    private lateinit var viewModel: ChildrenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_children, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChildrenViewModel::class.java)
        // TODO: Use the ViewModel
    }

}