package com.example.alfa_bank_android_app_parent_2.domain.entiies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryDish(
    var id:Int,
    var name: String,
    var weight: String,
    var sum: String,
    var date:String
) : Parcelable
