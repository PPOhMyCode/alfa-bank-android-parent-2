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

    companion object {
        fun newInstance() = NutritionHistoryFragment()
    }

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
        val dates = listOf("28 сентября", "29 сентября")
        val historyDish = listOf(
            HistoryDish("Каша овсяная", "150 г", "-85 р"),
            HistoryDish("Чай черный", "150 г", "-85 р"),
            HistoryDish("Творожная запеканка", "150 г", "-85 р"),
            HistoryDish("Компот из сухофруктов", "100г", "-80 р")
        )

        val historyListAdapter = HistoryListAdapter(
            dates,
            historyDish,
            listOf(Pair(1, 0), Pair(0, 0), Pair(0, 1), Pair(1, 1), Pair(0, 2), Pair(0, 3))
        )
        with(binding.recyclerView) {
            adapter = historyListAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            false
        }
        // TODO: Use the ViewModel
    }

}