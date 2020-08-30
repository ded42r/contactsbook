package com.example.contactsbook.validators

import com.example.contactsbook.exceptions.PhoneValidationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


interface IPhoneValidator {
    @Throws(PhoneValidationException::class)
    fun validatePhone(phone: String?)
}

@Service
class SimplePhoneValidator(private val regexString: String) : IPhoneValidator {
    @Autowired
    constructor() : this("^\\+\\d{11}\$")

    private val regex = regexString.toRegex()

    override fun validatePhone(phone: String?) {
        if (phone.isNullOrEmpty())
            throw PhoneValidationException("Phone must be not null or empty")

        if (!regex.matches(phone))
            throw PhoneValidationException("Phone is not valid. Regex: $regexString")
    }

}