package com.example.alfa_bank_android_app_parent_2.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentChildrenBinding
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentMenuBinding
import com.example.alfa_bank_android_app_parent_2.ui.adapters.DishListAdapter
import com.example.alfa_bank_android_app_parent_2.ui.children.ChildrenViewModel

class MenuFragment : Fragment() {
    private lateinit var viewModel: MenuViewModel

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater,container,false)
        viewModel = MenuViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridLayoutManager = GridLayoutManager(requireActivity(),2)
        with(binding.recyclerView){
            adapter =DishListAdapter(viewModel.getDishes())
            layoutManager = gridLayoutManager
            false
        }
    }
}