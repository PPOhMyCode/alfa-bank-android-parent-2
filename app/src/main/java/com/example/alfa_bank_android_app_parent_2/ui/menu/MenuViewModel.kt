package com.example.alfa_bank_android_app_parent_2.ui.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.alfa_bank_android_app_parent_2.data.PreferencesCopyImpl
import com.example.alfa_bank_android_app_parent_2.data.PreferencesUserImpl
import com.example.alfa_bank_android_app_parent_2.domain.PreferencesCopy
import com.example.alfa_bank_android_app_parent_2.domain.PreferencesUser
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.TypeOfMeal
import java.time.DayOfWeek

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private var _preferencesUser: PreferencesUserImpl = PreferencesUserImpl(application)
    private var _preferencesCopy: PreferencesCopyImpl = PreferencesCopyImpl(application)

    private var _addedDishes: MutableMap<DayOfWeek, MutableMap<TypeOfMeal, MutableMap<Dish, Int>>> =
        mutableMapOf()

    private var _dishCount: MutableMap<Int, Int> = mutableMapOf()

    fun loadDishes(typeOfMeal: TypeOfMeal, dayOfWeek: DayOfWeek): List<Dish>? {
        return when (typeOfMeal) {
            TypeOfMeal.BREAKFAST -> listOf(
                Dish(8, "Сыр", "Сыр", 30F, 30F, 1F, 1F, 1F, 1F),
                Dish(1, "Масло сливочное", "Масло сливочное", 30F, 20F, 2F, 1F, 2F, 1F),
                Dish(2, "Каша", "Пшено, рис", 150F, 85F, 1F, 123F, 213F, 31F),
                Dish(3, "Чай", "Вода, чай", 100F, 10F, 123F, 123F, 1231F, 2131F),
                Dish(4, "Яблоки", "Яблоки", 70F, 20F, 1123F, 1123F, 1231F, 2131F)
            )
            TypeOfMeal.DINNER -> listOf(
                Dish(5, "Рыба", "Рыба, томаты, овощи", 200F, 100F, 1F, 123F, 213F, 31F),
                Dish(6, "Чай", "Вода, чай", 100F, 10F, 1F, 1F, 1F, 1F),
                Dish(7, "Хлеб", "Хлеб", 100F, 2F, 1123F, 1123F, 1231F, 2131F),
            )
            TypeOfMeal.AFTERNOON_SNACK -> null
        }
    }

    fun addDish(dayOfWeek: DayOfWeek, typeOfMeal: TypeOfMeal, dish: Dish) {
        if (_addedDishes[dayOfWeek].isNullOrEmpty())
            _addedDishes[dayOfWeek] = mutableMapOf()
        if (_addedDishes[dayOfWeek]?.get(typeOfMeal).isNullOrEmpty())
            _addedDishes[dayOfWeek]?.set(typeOfMeal, mutableMapOf())
        if (_addedDishes[dayOfWeek]?.get(typeOfMeal)?.get(dish) == null) {
            _addedDishes[dayOfWeek]?.get(typeOfMeal)?.set(dish, 0)
        }
        val count = _addedDishes[dayOfWeek]?.get(typeOfMeal)?.get(dish)
        if (count != null) {
            _addedDishes[dayOfWeek]?.get(typeOfMeal)?.set(dish, count + 1)
            _dishCount[dish.id] = _dishCount[dish.id]?.plus(1) ?: 1
        } else
            throw RuntimeException("Count is null")
    }

    fun removeDish(dayOfWeek: DayOfWeek, typeOfMeal: TypeOfMeal, dish: Dish) {
        val count = _addedDishes[dayOfWeek]?.get(typeOfMeal)?.get(dish) ?: 0
        if (count > 0) {
            _addedDishes[dayOfWeek]?.get(typeOfMeal)?.set(dish, count - 1)
            _dishCount[dish.id] = _dishCount[dish.id]?.minus(1) ?: 0
        }
    }

    fun getDishCount() = _dishCount

    fun getTextFromDishes(dayOfWeek: DayOfWeek): String {
        val result = StringBuilder()
        if (_preferencesCopy.isNeedToDisplayName)
            result.append(
                "${_preferencesUser.userChild?.firstName} ${_preferencesUser.userChild?.lastName}" + "\n"
            )
        if (_preferencesCopy.isNeedToDisplayDay)
            result.append(dayOfWeek.translateDayToRussia() + "\n")
        val typesOfMeals =
            _addedDishes[dayOfWeek]?.keys ?: return "$dayOfWeek \n На Данный день не выбрано блюд"
        var bill = 0F
        var squirrels = 0F
        var fat = 0F
        var carbohydrates = 0F
        var calories =0F
        for (typeOfMeal in typesOfMeals) {
            result.append(typeOfMeal.translateDayToRussia() + "\n")
            val dishes = _addedDishes[dayOfWeek]?.get(typeOfMeal)!!.keys
            for (dish in dishes) {
                bill += dish.cost
                squirrels += dish.squirrels
                fat += dish.fat
                carbohydrates += dish.carbohydrates
                calories +=dish.calories
                val count = _addedDishes[dayOfWeek]?.get(typeOfMeal)
                    ?.get(dish) ?: 0
                if (count > 0)
                    result.append(
                        dish.name + " " + count + "ШТ" + "\n"
                    )
            }
        }
        if (_preferencesCopy.isNeedToDisplayBill)
            result.append("Стоимость: $bill\n")
        if (_preferencesCopy.isNeedToDisplaySquirrels)
            result.append("Белки: $squirrels\n")
        if (_preferencesCopy.isNeedToDisplayFat)
            result.append("Жиры: $fat\n")
        if(_preferencesCopy.isNeedToDisplayCarbohydrates)
            result.append("Углеводы: $carbohydrates\n")
        if(_preferencesCopy.isNeedToDisplayCalories)
            result.append("Ккал: $calories\n")
        return result.toString()
    }

    private fun Any.translateDayToRussia(): String {
        return when (this) {
            DayOfWeek.MONDAY -> "Понедельник"
            DayOfWeek.TUESDAY -> "Вторник"
            DayOfWeek.WEDNESDAY -> "Среда"
            DayOfWeek.THURSDAY -> "Четверг"
            DayOfWeek.FRIDAY -> "Пятница"
            DayOfWeek.SATURDAY -> "Суббота"
            DayOfWeek.SUNDAY -> "Воскресенье"
            TypeOfMeal.DINNER -> "Обед"
            TypeOfMeal.BREAKFAST -> "Завтрак"
            TypeOfMeal.AFTERNOON_SNACK -> "Полдник"
            else -> ""
        }
    }


}