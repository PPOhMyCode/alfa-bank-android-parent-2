package com.example.alfa_bank_android_app_parent_2.domain.entiies

import java.sql.Time
import java.time.DayOfWeek

class Notification(
    var idNotification: Int?,
    var daysOfWeek: List<DayOfWeek>,
    var time: Time,
    var requestCode: Int,
    var isNotOnPause:Boolean
)