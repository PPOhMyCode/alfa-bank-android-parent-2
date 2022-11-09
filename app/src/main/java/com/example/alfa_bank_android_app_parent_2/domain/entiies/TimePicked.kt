package com.example.alfa_bank_android_app_parent_2.domain.entiies

import java.sql.Time

class TimePicked(
    var monday: Boolean,
    var tuesday: Boolean,
    var wednesday: Boolean,
    var thursday: Boolean,
    var friday: Boolean,
    var saturday: Boolean,
    var sunday: Boolean,
    var time: Time
)