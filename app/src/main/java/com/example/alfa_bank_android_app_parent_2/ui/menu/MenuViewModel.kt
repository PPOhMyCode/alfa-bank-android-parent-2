package com.example.alfa_bank_android_app_parent_2.ui.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesCopyImpl
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesUserImpl
import com.example.alfa_bank_android_app_parent_2.data.repository.ParentRepositoryImpl
import com.example.alfa_bank_android_app_parent_2.domain.LoadDishesUseCase
import com.example.alfa_bank_android_app_parent_2.domain.ParentRepository
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.TypeOfMeal
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private var _preferencesUser: PreferencesUserImpl = PreferencesUserImpl(application)
    private var _preferencesCopy: PreferencesCopyImpl = PreferencesCopyImpl(application)
    private var _addedDishes: MutableMap<DayOfWeek, MutableMap<TypeOfMeal, MutableMap<Dish, Int>>> =
        mutableMapOf()
    private var _dishCount: MutableMap<Int, Int> = mutableMapOf()


    private var repository = ParentRepositoryImpl(application.applicationContext)
    private var loadChildrenUseCase = LoadDishesUseCase(repository)

    var dishes:MutableLiveData<List<Dish>> = MutableLiveData<List<Dish>>()


    fun loadDishes(typeOfMeal: TypeOfMeal, dayOfMonth: String)  {
        viewModelScope.launch {
            dishes.value =loadChildrenUseCase.invoke("2022-12-$dayOfMonth", typeOfMeal.value.toString())
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
            _addedDishes[dayOfWeek]?.keys ?: return "$dayOfWeek \n ???? ???????????? ???????? ???? ?????????????? ????????"
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
                        dish.name + " " + count + "????" + "\n"
                    )
            }
        }
        if (_preferencesCopy.isNeedToDisplayBill)
            result.append("??????????????????: $bill\n")
        if (_preferencesCopy.isNeedToDisplaySquirrels)
            result.append("??????????: $squirrels\n")
        if (_preferencesCopy.isNeedToDisplayFat)
            result.append("????????: $fat\n")
        if(_preferencesCopy.isNeedToDisplayCarbohydrates)
            result.append("????????????????: $carbohydrates\n")
        if(_preferencesCopy.isNeedToDisplayCalories)
            result.append("????????: $calories\n")
        return result.toString()
    }

    private fun Any.translateDayToRussia(): String {
        return when (this) {
            DayOfWeek.MONDAY -> "??????????????????????"
            DayOfWeek.TUESDAY -> "??????????????"
            DayOfWeek.WEDNESDAY -> "??????????"
            DayOfWeek.THURSDAY -> "??????????????"
            DayOfWeek.FRIDAY -> "??????????????"
            DayOfWeek.SATURDAY -> "??????????????"
            DayOfWeek.SUNDAY -> "??????????????????????"
            TypeOfMeal.DINNER -> "????????"
            TypeOfMeal.BREAKFAST -> "??????????????"
            TypeOfMeal.AFTERNOON_SNACK -> "??????????????"
            else -> ""
        }
    }


}