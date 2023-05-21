package com.example.alfa_bank_android_app_parent_2.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentNutritionHistoryBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.HistoryDish
import com.example.alfa_bank_android_app_parent_2.ui.adapters.HistoryListAdapter

class NutritionHistoryFragment : Fragment() {



    private lateinit var viewModel: NutritionHistoryViewModel

    private lateinit var binding: FragmentNutritionHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutritionHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[NutritionHistoryViewModel::class.java]
        viewModel.loadData()
        viewModel.historyDish.observe(requireActivity()) {
            binding.progressBar4.visibility = View.GONE
            it?.let { it1 ->
                initializeRecyclerView(it1)
            }
        }
    }

    private fun initializeRecyclerView(items: List<Any>) {
        val historyListAdapter = HistoryListAdapter(
            items
        )
        with(binding.recyclerView) {
            adapter = historyListAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            false
        }
    }

    companion object {
        fun newInstance() = NutritionHistoryFragment()
    }

}