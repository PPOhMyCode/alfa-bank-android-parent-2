package com.example.alfa_bank_android_app_parent_2.domain.entiies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Child(
    var name: String,
    var schoolClass: String,
    var school: String,
    var account: Float
):Parcelable