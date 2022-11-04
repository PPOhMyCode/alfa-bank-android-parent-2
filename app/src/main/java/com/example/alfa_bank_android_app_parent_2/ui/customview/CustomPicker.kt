package com.example.alfa_bank_android_app_parent_2.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.example.alfa_bank_android_app_parent_2.R
import com.google.android.material.textview.MaterialTextView


class CustomPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    var day2: String = "вт"

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.custom_picker, this, true)
        val day = findViewById<MaterialTextView>(R.id.day)

        day.text = day2

        orientation = VERTICAL
    }

    override fun callOnClick(): Boolean {
        return super.callOnClick()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event) // this super call is important !!!

        return true
    }





}