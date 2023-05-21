package com.example.alfa_bank_android_app_parent_2.data.mapper

import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.*
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import com.example.alfa_bank_android_app_parent_2.domain.entiies.HistoryDish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Parent
import com.google.gson.Gson

class ParentMapper {

    fun mapParentDtoToParent(parentDto: ParentDto): Parent {
        val testModel = Gson().fromJson(parentDto.user, UserInformation::class.java)
        return Parent(testModel.FirstName, testModel.SecondName, testModel.UserId,parentDto.url)
    }

    fun mapChildrenDtoToChild(childDto: ChildDto): Child {
        return with(childDto){
            Child(ChildrenId,
                FirstName,
                SecondName,
                GradeId,
                "",
                0.0F
            )
        }
    }

    fun mapHistoryDishDtoToHistoryDish(historyDishDtoItem: HistoryDishDtoItem):HistoryDish{

        return with(historyDishDtoItem){
            HistoryDish(dishId,name, weight.toString(),cost.toString(),date)
        }
    }
}