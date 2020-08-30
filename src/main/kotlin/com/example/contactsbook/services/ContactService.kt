package com.example.contactsbook.services

import com.example.contactsbook.containsByPredicate
import com.example.contactsbook.dto.ContactDTO
import com.example.contactsbook.dto.CreateContactDTO
import com.example.contactsbook.dto.PhoneDTO
import com.example.contactsbook.dto.UpdateContactDTO
import com.example.contactsbook.entity.ContactEntity
import com.example.contactsbook.entity.PhoneEntity
import com.example.contactsbook.entity.PhoneTypeEntity
import com.example.contactsbook.enum.PhoneType
import com.example.contactsbook.exceptions.ContactNotFoundException
import com.example.contactsbook.repository.ContactRepository
import com.example.contactsbook.repository.PhoneRepository
import com.example.contactsbook.repository.PhoneTypeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContactService(
        private val contactRepository: ContactRepository,
        private val phoneRepository: PhoneRepository,
        phoneTypeRepository: PhoneTypeRepository
) : IContactService {

    private val typeCache: Map<PhoneType, PhoneTypeEntity> by lazy {
        phoneTypeRepository.findAll()
                .map {
                    it.type to it
                }.toMap()
    }

    @Transactional
    override fun add(dto: CreateContactDTO): ContactDTO = dto.let {
        ContactEntity(
                it.firstName,
                it.lastName,
                it.address
        ).apply {
            phones = mapPhoneDTO2Entity(dto.phones, this)

        }
    }.let {
        save(it)
    }

    @Throws(ContactNotFoundException::class)
    @Transactional
    override fun update(dto: UpdateContactDTO): ContactDTO {
        val optionalContactEntity = contactRepository.findById(dto.id)
        if (optionalContactEntity.isPresent.not())
            throw ContactNotFoundException(dto.id)
        return updateContact(optionalContactEntity.get(), dto)

    }

    override fun getAll(): List<ContactDTO> = contactRepository.findAll().map {
        ContactDTO(
                it.id,
                it.firstName,
                it.lastName,
                it.address,
                mapPhoneEntity2Dto(it.phones)
        )
    }

    @Transactional
    override fun delete(ids: List<Int>) {
        contactRepository.deleteAllByIdIn(ids)
    }

    override fun search(query: String): List<ContactDTO> = contactRepository.findContacts(query).map {
        ContactDTO(
                it.id,
                it.firstName,
                it.lastName,
                it.address,
                mapPhoneEntity2Dto(it.phones)
        )
    }

    private fun save(entity: ContactEntity): ContactDTO = contactRepository.save(entity).let {
        ContactDTO(
                it.id,
                it.firstName,
                it.lastName,
                it.address,
                mapPhoneEntity2Dto(it.phones)
        )
    }

    private fun updateContact(contactEntity: ContactEntity, updateContactDTO: UpdateContactDTO): ContactDTO {

        val forRemoving = contactEntity.phones.filterNot { pe ->
            updateContactDTO.phones.containsByPredicate { it.number == pe.number }
        }

        val remainingPhones = (contactEntity.phones - forRemoving).toMutableSet()

        remainingPhones.forEach { pe ->
            pe.phoneType = getTypeEntity(updateContactDTO.phones.first { it.number == pe.number }.type)
        }

        remainingPhones.addAll(mapPhoneDTO2Entity(updateContactDTO.phones, contactEntity))

        contactEntity.apply {
            firstName = updateContactDTO.firstName
            lastName = updateContactDTO.lastName
            address = updateContactDTO.address
            phones = remainingPhones
        }

        val entity = save(contactEntity)
        phoneRepository.deleteAll(forRemoving)
        return entity
    }

    private fun mapPhoneDTO2Entity(dtos: List<PhoneDTO>, contactEntity: ContactEntity): MutableSet<PhoneEntity> =
            dtos.map { phoneDto ->
                PhoneEntity(

                        phoneDto.number,
                        getTypeEntity(phoneDto.type),
                        contactEntity
                )
            }.toMutableSet()

    private fun mapPhoneEntity2Dto(entities: Set<PhoneEntity>): List<PhoneDTO> = entities.map { phoneEntity ->
        PhoneDTO(
                phoneEntity.number,
                phoneEntity.phoneType.type
        )
    }.toList()

    private fun getTypeEntity(type: PhoneType): PhoneTypeEntity = typeCache[type] ?: error("Invalid phone type")
}

