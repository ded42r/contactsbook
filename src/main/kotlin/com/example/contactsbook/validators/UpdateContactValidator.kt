package com.example.contactsbook.validators

import com.example.contactsbook.dto.UpdateContactDTO
import com.example.contactsbook.exceptions.ContactNotFoundException
import com.example.contactsbook.repository.ContactRepository
import com.example.contactsbook.repository.PhoneRepository
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [UpdateContactValidator::class])
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class CorrectUpdateContact(
        val message: String = "Invalid value",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [])

class UpdateContactValidator(
        phoneRepository: PhoneRepository,
        phoneValidator: IPhoneValidator,
        private val contactRepository: ContactRepository

) : BaseContactValidator<CorrectUpdateContact, UpdateContactDTO>(phoneRepository, phoneValidator) {

    override fun validate(dto: UpdateContactDTO): Boolean {
        if (dto.id == 0) {
            return successResultOrException(
                    mapOf("id" to SHOULD_BE_NOT_EMPTY_MESSAGE),
                    dto
            )
        }

        val exist = contactRepository.existsById(dto.id)
        if (exist.not())
            throw ContactNotFoundException(dto.id)

        validateContact(dto)

        val notHisPhones = getPhonesByNumbers(dto.phones.map { it.number }).filterNot { it.contact.id == dto.id }
        if (notHisPhones.isNotEmpty()) {
            return successResultOrException(
                    createNotHisPhonesError(notHisPhones),
                    dto
            )
        }

        return true
    }
}