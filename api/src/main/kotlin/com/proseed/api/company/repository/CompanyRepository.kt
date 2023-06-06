package com.proseed.api.company.repository

import com.proseed.api.company.model.Company
import com.proseed.api.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CompanyRepository : JpaRepository<Company, Long> {
}