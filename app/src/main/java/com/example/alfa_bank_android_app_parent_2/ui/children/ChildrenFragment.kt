package com.example.alfa_bank_android_app_parent_2.ui.children

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentAuthenticationBinding
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentChildrenBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import com.example.alfa_bank_android_app_parent_2.ui.ChildActivity
import com.example.alfa_bank_android_app_parent_2.ui.adapters.ChildListAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration

class ChildrenFragment : Fragment() {


    private lateinit var viewModel: ChildrenViewModel

    private var _binding: FragmentChildrenBinding? = null
    private val binding: FragmentChildrenBinding
        get() = _binding ?: throw RuntimeException("FragmentChildrenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildrenBinding.inflate(inflater, container, false)
        viewModel = ChildrenViewModel(requireActivity().application)
        viewModel.loadChildren()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.children.observe(requireActivity()) {
            binding.progressBar2.visibility =View.GONE
            it?.let { it1 -> initializeRecyclerView(it1) }
        }
    }

    private fun initializeRecyclerView(children: List<Child>) {
        val childListAdapter = ChildListAdapter(children)
        childListAdapter.onItemClick = {
            viewModel.preferences.userChild = it
            val intent = ChildActivity.newIntent(requireActivity(), it)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
        val divider = MaterialDividerItemDecoration(
            requireActivity(),
            LinearLayoutManager.VERTICAL
        )
        divider.isLastItemDecorated = false
        with(binding.childrenRecyclerView) {
            adapter = childListAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            false
            addItemDecoration(divider)
        }
    }


}