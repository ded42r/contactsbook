package com.example.contactsbook.dto

open class ContactDTO(
        open val id: Int?,
        val firstName: String,
        val lastName: String?,
        val address: String?,
        val phones: List<PhoneDTO>
)