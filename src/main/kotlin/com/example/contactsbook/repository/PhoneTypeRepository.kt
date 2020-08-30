package com.example.contactsbook.repository

import com.example.contactsbook.entity.PhoneTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PhoneTypeRepository : JpaRepository<PhoneTypeEntity, Int>