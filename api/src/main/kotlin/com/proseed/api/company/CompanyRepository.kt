package com.proseed.api.company

import com.proseed.api.company.model.Company
import com.proseed.api.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CompanyRepository : JpaRepository<Company, Long> {

    @Query("select c from Company c where c.user.id = :user and c.name= :name")
    fun findByUserAndName(@Param("user")user: Long, @Param("name")name: String): Company?
}