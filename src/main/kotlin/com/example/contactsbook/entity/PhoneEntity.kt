package com.example.contactsbook.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
@Table(name = "phones")
open class PhoneEntity() {
    constructor(
            number: String,
            phoneType: PhoneTypeEntity,
            contact: ContactEntity
    ) : this() {
        this.number = number
        this.phoneType = phoneType
        this.contact = contact

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Int = 0

    @Column(name = "number", length = 12, nullable = false, unique = true)
    open lateinit var number: String

    @OneToOne
    open lateinit var phoneType: PhoneTypeEntity

    @ManyToOne
    @JsonBackReference
    open lateinit var contact: ContactEntity

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneEntity) return false

        if (number != other.number) return false

        return true
    }

    override fun hashCode(): Int {
        return number.hashCode()
    }


}