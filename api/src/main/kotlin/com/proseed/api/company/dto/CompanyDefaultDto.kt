package com.proseed.api.company.dto

import com.proseed.api.company.model.Company


data class CompanyDefaultDto(
    val company_name: String,
    val company_imageUrl: String,
    val company_introduction: String
) {
    constructor() : this("","","")
    constructor(company: Company) : this(
        company_name = company.name,
        company_imageUrl = company.imageUrl,
        company_introduction = company.introduction
    )
}