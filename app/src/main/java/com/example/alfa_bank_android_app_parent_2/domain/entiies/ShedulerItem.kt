package com.example.alfa_bank_android_app_parent_2.domain.entiies

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScheduleItem(
    val title: String,
    val description: String
) : Parcelable