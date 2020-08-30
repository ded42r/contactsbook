package com.example.contactsbook.repository

import com.example.contactsbook.entity.ContactEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ContactRepository : JpaRepository<ContactEntity, Int> {

    fun deleteAllByIdIn(ids: List<Int>)

    @Query("SELECT c from ContactEntity as c WHERE LOWER(c.firstName) LIKE LOWER (CONCAT ('%',:query,'%')) OR LOWER(c.lastName) LIKE LOWER (CONCAT ('%',:query,'%'))")
    fun findContacts(@Param("query") firstName: String): List<ContactEntity>

}