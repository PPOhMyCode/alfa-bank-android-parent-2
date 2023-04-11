package com.example.alfa_bank_android_app_parent_2.ui.receipts


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentReceiptsBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.ReceiptsItem
import com.example.alfa_bank_android_app_parent_2.ui.ChildActivity
import com.example.alfa_bank_android_app_parent_2.ui.adapters.DateAdapter
import com.example.alfa_bank_android_app_parent_2.ui.adapters.ReceiptsAdapter
import com.example.alfa_bank_android_app_parent_2.ui.receipt.ReceiptFragment
import com.google.android.material.search.SearchView.TransitionState.*


class ReceiptsFragment : Fragment() {

    private lateinit var binding: FragmentReceiptsBinding
    private lateinit var date:DateAdapter

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
        initSearch()
    }

    private fun initializeAutoCompleteTextView() {

    }

    private fun initializeRecyclerView() {
        val receiptsListAdapter = ReceiptsAdapter(receipts)
        receiptsListAdapter.onItemClickListener = {
            goToFragment(ReceiptFragment.newInstance(), it)
        }
        with(binding.receiptsRecyclerView) {
            adapter = receiptsListAdapter
            false
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initSearch() {
        with(binding) {

            date = DateAdapter(listOf("2023","2022","2021","2022"))

            date.onItemClickListener = {
                Log.d("aasdsd",it)
                searchView.editText.setText(it)
            }

            with(resultRecyclerView){
                adapter =date
                false
                layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
            }




            //!!!!

            searchView.editText.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        //searchView.hide()
                        //searchView.setVisible(false)
                        Log.d("texasdt",s.toString())
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        //searchView.hide()
                        Log.d("texasdt",s.toString())
                    }

                    override fun afterTextChanged(s: Editable) {
                        //searchView.hide()
                        Log.d("texasdt","qwe3")
                    }
                })

            //!!!!




            searchView.addTransitionListener { searchView, previousState, newState ->
                Log.d("adf", "ads1232")

                when(newState){
                    HIDING -> (activity as ChildActivity).openAppBarMain()
                    HIDDEN -> Unit
                    SHOWING -> (activity as ChildActivity).closeAppBarMain()
                    SHOWN ->  Unit
                }
               // (activity as ChildActivity).closeAppBarMain()
            }


            searchView.hide()


            searchView.inflateMenu(R.menu.main)


            searchView
                .editText
                .setOnEditorActionListener { v, actionId, event ->
                    Log.d("adf", "ads3")
                    //(activity as ChildActivity).closeAppBarMain()
                    //(activity as ChildActivity).openAppBarMain()
                    searchBar.text = searchView.text
                    searchView.hide()
                    false
                }

            searchView.editText.setOnTouchListener { v, event ->
                Log.d("adf", "das")
                //(activity as ChildActivity).closeAppBarMain()
                true
            }



            searchView.editText.setOnClickListener {
                Log.d("adf", "das")
                // Log.d("adf", "ads2")
                //(activity as ChildActivity).closeAppBarMain()
            }

            // searchBar.setNavigationOnClickListener {
            //     Log.d("adf", "ads")
            //     (activity as ChildActivity).closeAppBarMain()
            // }


        }
        // binding

        // binding.searchView.setOnMenuItemClickListener { menuItem -> true }
    }

    private fun goToFragment(fragment: Fragment, receiptsItem: ReceiptsItem) {
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