package com.example.alfa_bank_android_app_parent_2.ui.sheduler

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentReceiptsBinding
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentSchedulerBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.ReceiptsItem
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Schedule
import com.example.alfa_bank_android_app_parent_2.ui.adapters.ReceiptsAdapter
import com.example.alfa_bank_android_app_parent_2.ui.adapters.ShedulerAdapter
import com.example.alfa_bank_android_app_parent_2.ui.receipt.ReceiptFragment
import com.example.alfa_bank_android_app_parent_2.ui.receipts.ReceiptsFragment
import com.example.alfa_bank_android_app_parent_2.ui.settings.SettingsFragment

class SchedulerFragment : Fragment() {



    private lateinit var viewModel: SchedulerViewModel

    private lateinit var binding: FragmentSchedulerBinding

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
        //var adapter = ShedulerAdapter(listOf(Schedule("10.15 - 10.30","После 2 урока"),
        //    Schedule("12.20 - 12.40","После 4 урока"),
        //    Schedule("14.15 - 14.30","После 5 урока")
        //))

        val receiptsListAdapter = ShedulerAdapter()

        with(binding.asd) {
            adapter = receiptsListAdapter
            false
            layoutManager =
                LinearLayoutManager(requireActivity(), androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        }

        //with(binding.asd){
        //    adapter =adapter
        //    false
        //    layoutManager =LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        //}
    }

    companion object {
        fun newInstance() = SchedulerFragment()

        private val receipts = listOf(
            ReceiptsItem("Январь", "2023"),
            ReceiptsItem("Февраль", "2023"),
            ReceiptsItem("Март", "2023"),
            ReceiptsItem("Апрель", "2023"),
            ReceiptsItem("Май", "2023"),
            ReceiptsItem("Июнь", "2023"),
            ReceiptsItem("Июль", "2023"),
            ReceiptsItem("Август", "2023"),
            ReceiptsItem("Сентябрь", "2023"),
            ReceiptsItem("Октябрь", "2023"),
            ReceiptsItem("Ноябрь", "2023"),
            ReceiptsItem("Декабрь", "2023")
        )
    }
}