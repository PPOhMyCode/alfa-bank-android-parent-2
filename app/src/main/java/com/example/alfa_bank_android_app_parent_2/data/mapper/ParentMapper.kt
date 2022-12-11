package com.example.alfa_bank_android_app_parent_2.data.mapper

import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.ChildDto
import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.ParentDto
import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.UserInformation
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Parent
import com.google.gson.Gson

class ParentMapper {

    fun mapParentDtoToParent(parentDto: ParentDto): Parent {
        val testModel = Gson().fromJson(parentDto.user, UserInformation::class.java)
        return Parent(testModel.FirstName, testModel.SecondName, testModel.UserId)
    }

    fun mapChildrenDtoToChild(childDto: ChildDto): Child {
        return with(childDto){
            Child(ChildrenId,
                FirstName,
                SecondName,
                GradeId.toString(),
                "",
                0.0F
            )
        }
    }
}