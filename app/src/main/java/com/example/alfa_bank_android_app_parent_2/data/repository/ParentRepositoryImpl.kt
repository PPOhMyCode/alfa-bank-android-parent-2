package com.example.alfa_bank_android_app_parent_2.data.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.alfa_bank_android_app_parent_2.data.mapper.ParentMapper
import com.example.alfa_bank_android_app_parent_2.data.network.ApiFactory
import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.AuthorizeBodyDto
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesUserImpl
import com.example.alfa_bank_android_app_parent_2.domain.ParentRepository
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Parent
import retrofit2.HttpException
import java.lang.Exception


class ParentRepositoryImpl(var context: Context) : ParentRepository {

    private val preferencesUserImpl = PreferencesUserImpl(context)

    private val bdApiFactory = ApiFactory.getBdApiService()
    private val userApiFactory = ApiFactory.getUserApiService()

    private val mapper = ParentMapper()

    override suspend fun authorizeParent(login: String, password: String): Parent? = try {
        val parentDto = bdApiFactory.authorizeParent(AuthorizeBodyDto(login, password))
        mapper.mapParentDtoToParent(parentDto)
    } catch (
        e: HttpException
    ) {
        if (e.code() == 300)
            Toast.makeText(context, "Введет неверный логин или пароль", Toast.LENGTH_SHORT).show()
        else if (e.code() == 502)
            Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show()
        null
    }

    override suspend fun loadChildren(): List<Child>? = try {
        val userId = preferencesUserImpl.user?.id ?: 0
        userApiFactory.loadChildren(userId.toString()).map {
            mapper.mapChildrenDtoToChild(it)
        }
    } catch (e: HttpException) {
        if (e.code() == 502)
            Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show()
        null
    }

    override suspend fun loadDishes(date: String, type: String): List<Dish>? =
        try {
            val dishDtoContainer = userApiFactory.loadDishes(date, type)
            val result = mutableListOf<Dish>()
            for (dishDto in dishDtoContainer) {
                val descriptionDish = userApiFactory.loadDish(dishDto.DishId.toString())
                result.add(
                    Dish(
                        dishDto.DishId,
                        descriptionDish.Name,
                        descriptionDish.Description,
                        descriptionDish.Weight.toFloat(),
                        descriptionDish.Cost.toFloat(),
                        descriptionDish.Calories.toFloat(),
                        descriptionDish.Proteins.toFloat(),
                        descriptionDish.Fats.toFloat(),
                        descriptionDish.Carbohydrates.toFloat()
                    )
                )
            }
            result
        } catch (e: Exception) {
            null
        }
}