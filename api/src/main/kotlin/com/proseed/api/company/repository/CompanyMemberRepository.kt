package com.proseed.api.company.repository

import com.proseed.api.company.model.CompanyMember
import com.proseed.api.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CompanyMemberRepository : JpaRepository<CompanyMember, Long>, CompanyMemberRepositoryCustom {

}