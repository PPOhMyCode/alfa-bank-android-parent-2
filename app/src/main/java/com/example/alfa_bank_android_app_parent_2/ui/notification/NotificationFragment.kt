package com.example.alfa_bank_android_app_parent_2.ui.notification

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.shape.CornerFamily
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class NotificationFragment : Fragment() {
    //private lateinit var viewModel: NotificationViewModel
    private lateinit var binding: FragmentNotificationBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[NotificationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeButton()
        initializeBottomSheet()
    }

    private fun initializeButton() {
        initializeSetTimeButton()
        initializeNotificationButton()
        initializeCloseButton()
        initializeAcceptButton()
    }

    private fun initializeBottomSheet() {
        binding.notificationTimeTextView.setIs24HourView(true)

        //binding.bottomSheet.shapeAppearanceModel.
         //   .setAllCorners(CornerFamily.ROUNDED, 0F).build()
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        //    binding.bottomSheet.shapeAppearanceModel.toBuilder()
        //        .setAllCorners(CornerFamily.ROUNDED, 0F)
        //}
        with(BottomSheetBehavior.from(binding.bottomSheet)) {
            addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                        //binding.addNotificationButton.visibility=View.GONE

                        //binding.addNotificationButton.animate().scaleX(0F).scaleY(0F)
                        //    .setDuration(300).start()
                    } else if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                        binding.bottomSheet.shapeAppearanceModel.toBuilder()
                            .setAllCorners(CornerFamily.ROUNDED, 0F)
                        //binding.addNotificationButton.visibility=View.GONE
                        //binding.addNotificationButton.animate().scaleX(1F).scaleY(1F)
                        //    .setDuration(300).start()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    private fun initializeCloseButton() {
        binding.closeBottomSheetButton.setOnClickListener {
            hiddenBottomSheet()
        }
    }

    private fun initializeAcceptButton() {
        binding.acceptBottomSheetButton.setOnClickListener {
            hiddenBottomSheet()
        }
    }

    private fun hiddenBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).setState(BottomSheetBehavior.STATE_HIDDEN)
    }

    private fun initializeNotificationButton() {
        binding.addNotificationButton.setOnClickListener {
            BottomSheetBehavior.from(binding.bottomSheet)
                .setState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }

    private fun initializeSetTimeButton() {
        //binding.setTimeButton.setOnClickListener {
        //    val picker =
        //        MaterialTimePicker.Builder()
        //            .setTimeFormat(TimeFormat.CLOCK_24H)
        //            .setHour(12)
        //            .setMinute(10)
        //            .setTitleText("Select Appointment time")
        //            .build()
        //    picker.show(childFragmentManager, "tag")
        //}
    }


    companion object {
        fun newInstance() = NotificationFragment()
    }

}