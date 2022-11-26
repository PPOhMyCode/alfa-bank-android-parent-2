package com.example.alfa_bank_android_app_parent_2.domain

import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child

abstract class PreferencesUser() {
    abstract var isUserLogged : Boolean
    abstract var userPinCode: String?
    abstract var userChild:Child?

}