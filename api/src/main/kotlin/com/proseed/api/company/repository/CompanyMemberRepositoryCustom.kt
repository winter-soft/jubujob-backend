package com.proseed.api.company.repository

import com.proseed.api.company.model.CompanyMember
import com.proseed.api.user.model.User

interface CompanyMemberRepositoryCustom {
    open fun findByUserAndName(_user: User, name: String): CompanyMember?
}