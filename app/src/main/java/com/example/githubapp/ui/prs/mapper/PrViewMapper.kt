package com.example.githubapp.ui.prs.mapper

import com.example.githubapp.domain.model.DomainPrData
import com.example.githubapp.ui.prs.model.UiPrData
import com.example.githubapp.presentation.mapper.UserViewMapper
import com.example.githubapp.presentation.views.views.utils.DateTimeUtils
import com.example.githubapp.ui.ViewMapper
import javax.inject.Inject

class PrViewMapper @Inject constructor(private val userViewMapper: UserViewMapper) :
    ViewMapper<UiPrData, DomainPrData> {

    override fun mapToView(type: DomainPrData) =
        UiPrData(
            id = type.id,
            prTitle = type.title,
            prDesc = type.desc,
            user = userViewMapper.mapToView(type.user),
            closedAt = getValidDate(type.closedAt),
            createdAt = getValidDate(type.createdAt)
        )

    private fun getValidDate(date : String) : String {
        return if(date.isValid()){
            DateTimeUtils.getDayWithMonthName(date)
        } else
            "No date available."
    }

    }

fun String?.isValid(): Boolean {
    return !this.isNullOrEmpty() && this.isNotBlank()
}