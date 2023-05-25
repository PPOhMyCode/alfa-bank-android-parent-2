package com.example.alfa_bank_android_app_parent_2.ui.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesChildImpl
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesCopyImpl
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesUserImpl
import com.example.alfa_bank_android_app_parent_2.data.repository.ParentRepositoryImpl
import com.example.alfa_bank_android_app_parent_2.domain.LoadDishesThisWeekUseCase
import com.example.alfa_bank_android_app_parent_2.domain.LoadDishesUseCase
import com.example.alfa_bank_android_app_parent_2.domain.MakeOrderUseCase
import com.example.alfa_bank_android_app_parent_2.domain.ParentRepository
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.TypeOfMeal
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private var _preferencesUser: PreferencesUserImpl = PreferencesUserImpl(application)
    private var _preferencesCopy: PreferencesCopyImpl = PreferencesCopyImpl(application)
    private var _addedDishes: MutableMap<DayOfWeek, MutableMap<TypeOfMeal, MutableMap<Dish, Int>>> =
        mutableMapOf()
    private var _dishCount: MutableMap<Int, Int> = mutableMapOf()
    private var repository = ParentRepositoryImpl(application.applicationContext)
    private val loadDishesThisWeekUseCase = LoadDishesThisWeekUseCase(repository)
    private var loadDishesUseCase = LoadDishesUseCase(repository)
    private var makeOrderUseCase = MakeOrderUseCase(repository)
    private var childId = PreferencesChildImpl(application.applicationContext).idChild

    var statusOrder: MutableLiveData<String> = MutableLiveData<String>()

    var dishes: MutableLiveData<List<Dish>> = MutableLiveData<List<Dish>>()


    fun loadDishes(typeOfMeal: TypeOfMeal, dayOfMonth: String, mode: String) {

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date()).split("/")
        val year = currentDate[2].split(" ")[0]
        val month = currentDate[1]



        viewModelScope.launch {
            when (mode) {
                MenuFragment.LOAD_MENU_MODE -> {
                    dishes.value =
                        loadDishesUseCase.invoke(
                            "$year-0$month-$dayOfMonth",
                            typeOfMeal.value.toString()
                        )
                }
                MenuFragment.CHOOSE_MENU_MODE -> {
                    dishes.value = loadDishesThisWeekUseCase.invoke(
                        "$year-0$month-$dayOfMonth",
                        typeOfMeal.value.toString()
                    )
                }
            }
        }
    }


    fun makeOrders(dayOfWeek: DayOfWeek, dayOfMonth: String, typeOfMeal: TypeOfMeal) {
        viewModelScope.launch {
            val dishCounts = _addedDishes[dayOfWeek]?.get(typeOfMeal)?.entries
            dishCounts?.let {
                for (dishCount in it) {
                    for (count in 0 until dishCount.value) {
                        makeOrderUseCase.invoke(
                            "2023-05-$dayOfMonth",
                            childId,
                            typeOfMeal.value,
                            dishCount.key.id
                        )
                    }
                }
            }
            statusOrder.value = "Заказ оформлен"
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
        var calories = 0F
        for (typeOfMeal in typesOfMeals) {
            result.append(typeOfMeal.translateDayToRussia() + "\n")
            val dishes = _addedDishes[dayOfWeek]?.get(typeOfMeal)!!.keys
            for (dish in dishes) {
                bill += dish.cost
                squirrels += dish.squirrels
                fat += dish.fat
                carbohydrates += dish.carbohydrates
                calories += dish.calories
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
        if (_preferencesCopy.isNeedToDisplayCarbohydrates)
            result.append("Углеводы: $carbohydrates\n")
        if (_preferencesCopy.isNeedToDisplayCalories)
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