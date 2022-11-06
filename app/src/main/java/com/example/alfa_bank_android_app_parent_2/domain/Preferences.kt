package com.example.alfa_bank_android_app_parent_2.domain

import android.content.Context
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child

abstract class Preferences() {
    abstract var isUserLogged : Boolean
    abstract var userPinCode: String?
    abstract var userChild:Child?
}