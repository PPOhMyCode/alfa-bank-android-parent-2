package com.example.alfa_bank_android_app_parent_2.ui.iseat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.ActivityIsEatBinding
import com.example.alfa_bank_android_app_parent_2.ui.menu.MenuFragment
import java.time.DayOfWeek

class IsEatFragment: Fragment() {

    private lateinit var binding: ActivityIsEatBinding

    private var m:Boolean = true
    private var tu:Boolean = true
    private var w:Boolean = true
    private var th:Boolean = true
    private var f:Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivityIsEatBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDefaultBackgroundColorDays()
        initializeClick()
    }

    private fun initializeClick(){
        binding.mondayCardView.setOnClickListener {
            if(m) {
                binding.mondayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            } else{
                binding.mondayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
            }
            m = !m

        }
        binding.tuesdayCardView.setOnClickListener {

            if(tu) {
                binding.tuesdayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            } else{
                binding.tuesdayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
            }
            tu = !tu

        }
        binding.wednesdayCardView.setOnClickListener {

            binding.wednesdayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))

            if(w) {
                binding.wednesdayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            } else{
                binding.wednesdayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
            }
            w = !w

        }
        binding.thursdayCardView.setOnClickListener {

            binding.thursdayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))

            if(th) {
                binding.thursdayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            } else{
                binding.thursdayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
            }
            th = !th

        }
        binding.fridayCardView.setOnClickListener {

            binding.fridayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))

            if(f) {
                binding.fridayCardView.setCardBackgroundColor(resources.getColor(R.color.eafbe2))
            } else{
                binding.fridayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
            }
            f = !f

        }

        binding.makeOrderButton.setOnClickListener{
            Toast.makeText(requireContext(),"Питание отменено",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDefaultBackgroundColorDays() {
        binding.mondayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.tuesdayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.wednesdayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.thursdayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
        binding.fridayCardView.setCardBackgroundColor(resources.getColor(R.color.fbfff9))
    }

    companion object {
        fun newInstance(): IsEatFragment = IsEatFragment().apply {

        }
    }
}