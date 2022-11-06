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
import com.example.alfa_bank_android_app_parent_2.ui.ChildActivity
import com.example.alfa_bank_android_app_parent_2.ui.adapters.ChildListAdapter

class ChildrenFragment : Fragment() {


    private lateinit var viewModel: ChildrenViewModel

    private var _binding: FragmentChildrenBinding? = null
    private val binding: FragmentChildrenBinding
        get() = _binding ?: throw RuntimeException("FragmentChildrenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentChildrenBinding.inflate(inflater, container, false)
        viewModel= ChildrenViewModel( requireActivity().application)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.childrenRecyclerView.adapter
        val childListAdapter=ChildListAdapter(viewModel.loadChildren())
        childListAdapter.onItemClick={
            viewModel.preferences.userChild=it
            val intent = Intent(requireActivity(),ChildActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
        with(binding.childrenRecyclerView){
            adapter=childListAdapter
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
            false

        }
    }

    //override fun onActivityCreated(savedInstanceState: Bundle?) {
    //    super.onActivityCreated(savedInstanceState)
    //    viewModel = ViewModelProvider(this).get(ChildrenViewModel::class.java)
        // TODO: Use the ViewModel
    //}

}