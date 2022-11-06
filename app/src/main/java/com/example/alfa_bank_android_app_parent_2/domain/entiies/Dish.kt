package com.example.alfa_bank_android_app_parent_2.domain.entiies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dish(
    var name: String,
    var composition: List<String>,
    var weight: Float,
    var cost: Float
) : Parcelable