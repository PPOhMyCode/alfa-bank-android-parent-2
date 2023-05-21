package com.example.alfa_bank_android_app_parent_2.ui.sheduler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentSchedulerBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.ReceiptsItem
import com.example.alfa_bank_android_app_parent_2.ui.adapters.ShedulerAdapter

class SchedulerFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[SchedulerViewModel::class.java]
    }

    private lateinit var binding: FragmentSchedulerBinding
    private var receiptsListAdapter = ShedulerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSchedulerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding.asd) {
            adapter = receiptsListAdapter
            false
            layoutManager =
                LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
        }

        viewModel.shedulers.observe(requireActivity()){
            receiptsListAdapter.shedulers = it
            receiptsListAdapter.notifyDataSetChanged()
        }

        viewModel.loadSheduler()
    }

    companion object {
        fun newInstance() = SchedulerFragment()

    }
}