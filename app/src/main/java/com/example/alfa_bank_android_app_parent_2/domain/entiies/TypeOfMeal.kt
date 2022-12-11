package com.example.alfa_bank_android_app_parent_2.domain.entiies

enum class TypeOfMeal(val value: Int) {
    BREAKFAST(1),
    DINNER(2),
    AFTERNOON_SNACK(3);

    companion object {
        fun fromInt(value: Int) = TypeOfMeal.values().first { it.value == value }
    }
}