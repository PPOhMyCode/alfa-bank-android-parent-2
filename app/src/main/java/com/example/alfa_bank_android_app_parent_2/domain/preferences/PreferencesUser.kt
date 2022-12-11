package com.example.alfa_bank_android_app_parent_2.domain.preferences

import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Parent

abstract class PreferencesUser() {
    abstract var user : Parent?
    abstract var userPinCode: String?
    abstract var userChild:Child?

}