package com.example.contactsbook.services

import com.example.contactsbook.dto.ContactDTO
import com.example.contactsbook.dto.CreateContactDTO
import com.example.contactsbook.dto.UpdateContactDTO
import com.example.contactsbook.exceptions.ContactNotFoundException

interface IContactService {
    fun add(dto: CreateContactDTO): ContactDTO

    @Throws(ContactNotFoundException::class)
    fun update(dto: UpdateContactDTO): ContactDTO

    fun getAll(): List<ContactDTO>

    fun delete(ids: List<Int>)

    fun search(query: String): List<ContactDTO>
}