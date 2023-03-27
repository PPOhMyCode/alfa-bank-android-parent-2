package com.example.alfa_bank_android_app_parent_2.ui.receipts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentReceiptsBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.ReceiptsItem
import com.example.alfa_bank_android_app_parent_2.ui.ChildActivity
import com.example.alfa_bank_android_app_parent_2.ui.adapters.ReceiptsAdapter
import com.example.alfa_bank_android_app_parent_2.ui.notification.NotificationViewModel
import com.example.alfa_bank_android_app_parent_2.ui.receipt.ReceiptFragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class ReceiptsFragment : Fragment() {

    private lateinit var binding: FragmentReceiptsBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[ReceiptsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceiptsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        initializeAutoCompleteTextView()
    }

    private fun initializeAutoCompleteTextView() {

    }

    private fun initializeRecyclerView() {
        val receiptsListAdapter = ReceiptsAdapter(receipts)
        receiptsListAdapter.onItemClickListener = {
            goToFragment(ReceiptFragment.newInstance(),it)
        }
        with(binding.receiptsRecyclerView) {
            adapter = receiptsListAdapter
            false
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun goToFragment(fragment: Fragment,receiptsItem: ReceiptsItem) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.nav_host_fragment_content_child, fragment)
            ?.commit()
        (activity as ChildActivity).closeDrawer(receiptsItem)
        //binding.drawerLayout.closeDrawer(GravityCompat.START)
        //activity.findViewById<>()
        //closeDrawerLayout(fragment)
    }

    companion object {
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