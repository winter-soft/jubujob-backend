package com.proseed.api.company

import com.proseed.api.company.model.Company
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository : JpaRepository<Company, Long> {
}