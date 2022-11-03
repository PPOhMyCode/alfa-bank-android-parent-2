package com.example.alfa_bank_android_app_parent_2.domain

import android.content.Context

abstract class Preferences(context: Context) {
    abstract var isUserLogged : Boolean
    abstract var userPinCode: String?
}