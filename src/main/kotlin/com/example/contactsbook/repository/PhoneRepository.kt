package com.example.contactsbook.repository

import com.example.contactsbook.entity.PhoneEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface PhoneRepository : JpaRepository<PhoneEntity, Int> {
    fun findAllByNumberIn(numbers: List<String>): List<PhoneEntity>
}
