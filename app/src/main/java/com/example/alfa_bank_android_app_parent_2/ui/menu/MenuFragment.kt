package com.example.alfa_bank_android_app_parent_2.ui.menu

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentMenuBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.TypeOfMeal
import com.example.alfa_bank_android_app_parent_2.ui.adapters.DishListAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class MenuFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MenuViewModel::class.java]
    }

    private lateinit var dishListAdapter: DishListAdapter
    private var typeOfMeal = TypeOfMeal.BREAKFAST
    private var dayOfWeek = DayOfWeek.MONDAY
    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding == null")

    private lateinit var mode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMode()
        initializeRecyclerView()
        initializeMondayAndBreakfast()
        initializeDayClickListener()
        initializeTypeMealClickListener()
        initializeDays()
        loadDishes()
        initializeMakeOrder()
        initializeButtonSheet()
    }

    override fun onStart() {
        super.onStart()
        requireActivity().findViewById<ImageButton>(R.id.copyImageButton).setOnClickListener {
            copyText(viewModel.getTextFromDishes(dayOfWeek))
        }
    }

    private fun initializeMode() {
        mode = this.arguments?.getString(MODE) ?: CHOOSE_MENU_MODE
        if (mode == CHOOSE_MENU_MODE) {
            binding.makeOrderButton.visibility = View.GONE
        }
    }

    private fun initializeButtonSheet() {
        with(binding.dishMaterialCardView) {
            BottomSheetBehavior.from(this).state =
                BottomSheetBehavior.STATE_HIDDEN
            BottomSheetBehavior.from(this)
                .addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        when (newState) {
                            BottomSheetBehavior.STATE_HIDDEN -> binding.backgroundColorFrameLayout.visibility =
                                View.GONE
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    }

                })
        }
    }

    private fun initializeMakeOrder() {
        binding.makeOrderButton.setOnClickListener {
            viewModel.makeOrders(dayOfWeek, dayOfWeek.dayOfMonth().toString(), typeOfMeal)
        }
        viewModel.statusOrder.observe(requireActivity()) {
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun initializeDays() {
        binding.dataMonday.text = DayOfWeek.MONDAY.dayOfMonth().toString()
        binding.dataTuesday.text = DayOfWeek.TUESDAY.dayOfMonth().toString()
        binding.dataWednesday.text = DayOfWeek.WEDNESDAY.dayOfMonth().toString()
        binding.dataThursday.text = DayOfWeek.THURSDAY.dayOfMonth().toString()
        binding.dataFriday.text = DayOfWeek.FRIDAY.dayOfMonth().toString()
    }

    private fun initializeMondayAndBreakfast() {
        setDefaultBackgroundColorTypeMeal()
        setDefaultBackgroundColorDays()
        binding.mondayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
        binding.breakfastCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
    }

    private fun initializeRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        dishListAdapter = DishListAdapter(mode)
        dishListAdapter.onAddItemClick = {
            viewModel.addDish(dayOfWeek, typeOfMeal, it)
            dishListAdapter.dishCount = viewModel.getDishCount()
        }
        dishListAdapter.onDeleteItemClick = {
            viewModel.removeDish(dayOfWeek, typeOfMeal, it)
            dishListAdapter.dishCount = viewModel.getDishCount()
        }

        dishListAdapter.onImageItemClick = {
            openBottomSheetBehavior(it)
        }

        with(binding.recyclerView) {
            adapter = dishListAdapter
            layoutManager = gridLayoutManager
            false
        }
    }

    private fun openBottomSheetBehavior(dish: Dish) {
        with(binding.dishMaterialCardView) {
            binding.backgroundColorFrameLayout.visibility = View.VISIBLE
            BottomSheetBehavior.from(this).state =
                BottomSheetBehavior.STATE_EXPANDED
        }
        with(dish) {
            Picasso.get().load("https://storage.yandexcloud.net/systemimg/${dish.id}.png")
                .into(binding.dishImage)
            binding.dishTitleTextView.text = name
            binding.dishWeight.text = weight.toString()
            binding.caloriesTextView.text = calories.toString()
            binding.squirrelsTextView.text = squirrels.toString()
            binding.fatTextView.text = fat.toString()
            binding.carbohydratesTextView.text = carbohydrates.toString()
            binding.compositionTextView.text = composition
        }
    }


    private fun loadDishes() {
        viewModel.loadDishes(typeOfMeal, dayOfWeek.dayOfMonth().toString(), mode)
        viewModel.dishes.observe(requireActivity()) {
            binding.progressBar3.visibility = View.GONE
            val dishes = it
            if (dishes.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.INVISIBLE
                binding.noDishTextView.visibility = View.VISIBLE

            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.noDishTextView.visibility = View.INVISIBLE
                dishListAdapter.dishes = dishes
                dishListAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun copyText(text: String) {
        val myClipboard = requireActivity().applicationContext.getSystemService(
            CLIPBOARD_SERVICE
        ) as ClipboardManager
        val myClip = ClipData.newPlainText("text", text)
        myClipboard.setPrimaryClip(myClip)
        Toast.makeText(requireActivity(), "Текст скопирован", Toast.LENGTH_SHORT).show();
    }

    private fun initializeTypeMealClickListener() {
        binding.progressBar3.visibility = View.VISIBLE
        binding.breakfastCardView.setOnClickListener {
            setDefaultBackgroundColorTypeMeal()
            binding.breakfastCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            if (typeOfMeal != TypeOfMeal.BREAKFAST) {
                typeOfMeal = TypeOfMeal.BREAKFAST
                loadDishes()
            }
        }
        binding.dinnerCardView.setOnClickListener {
            setDefaultBackgroundColorTypeMeal()
            binding.dinnerCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            if (typeOfMeal != TypeOfMeal.DINNER) {
                typeOfMeal = TypeOfMeal.DINNER
                loadDishes()
            }
        }
        binding.afternoonSnackCardView.setOnClickListener {
            setDefaultBackgroundColorTypeMeal()
            binding.afternoonSnackCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            if (typeOfMeal != TypeOfMeal.AFTERNOON_SNACK) {
                typeOfMeal = TypeOfMeal.AFTERNOON_SNACK
                loadDishes()
            }
        }
    }

    private fun setDefaultBackgroundColorTypeMeal() {
        binding.breakfastCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.dinnerCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.afternoonSnackCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
    }

    private fun setDefaultBackgroundColorDays() {
        binding.mondayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.tuesdayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.wednesdayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.thursdayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.fridayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
    }

    private fun initializeDayClickListener() {
        binding.progressBar3.visibility = View.VISIBLE
        binding.mondayCardView.setOnClickListener {
            setDefaultBackgroundColorDays()
            binding.mondayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            dayOfWeek = DayOfWeek.MONDAY
            loadDishes()
        }
        binding.tuesdayCardView.setOnClickListener {
            setDefaultBackgroundColorDays()
            binding.tuesdayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            dayOfWeek = DayOfWeek.TUESDAY
            loadDishes()
        }
        binding.wednesdayCardView.setOnClickListener {
            setDefaultBackgroundColorDays()
            binding.wednesdayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            dayOfWeek = DayOfWeek.WEDNESDAY
            loadDishes()
        }
        binding.thursdayCardView.setOnClickListener {
            setDefaultBackgroundColorDays()
            binding.thursdayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            dayOfWeek = DayOfWeek.THURSDAY
            loadDishes()
        }
        binding.fridayCardView.setOnClickListener {
            setDefaultBackgroundColorDays()
            binding.fridayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            dayOfWeek = DayOfWeek.FRIDAY
            loadDishes()
        }
    }

    fun DayOfWeek.dayOfMonth(dateInWeek: LocalDate = LocalDate.now()): Int {
        val firstDateOfWeek = dateInWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        val dateOfDayOfWeek = firstDateOfWeek.with(TemporalAdjusters.nextOrSame(this))
        return when (mode) {
            CHOOSE_MENU_MODE -> dateOfDayOfWeek.dayOfMonth
            LOAD_MENU_MODE -> dateOfDayOfWeek.dayOfMonth + 7
            else -> throw Exception()
        }
    }

    companion object {
        fun newInstance(mode: String): MenuFragment = MenuFragment().apply {
            arguments = Bundle().apply {
                putString(MODE, mode)
            }
        }

        private const val MODE = "MODE"
        const val CHOOSE_MENU_MODE = "CHOOSE_MENU_MODE"
        const val LOAD_MENU_MODE = "LOAD_MENU_MODE"
    }

}