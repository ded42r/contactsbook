package com.example.contactsbook.dto

import com.example.contactsbook.validators.CorrectCreateContact

@CorrectCreateContact
class CreateContactDTO(
        firstName: String,
        lastName: String?,
        address: String?,
        phones: List<PhoneDTO>
) : ContactDTO(
        null,
        firstName,
        lastName,
        address,
        phones

)