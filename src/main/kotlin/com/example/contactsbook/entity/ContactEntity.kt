package com.example.contactsbook.entity

import javax.persistence.*


@Entity
@Table(name = "contacts")
open class ContactEntity() {

    constructor(
            firstName: String,
            lastName: String?,
            address: String?) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.address = address

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Int = 0

    @Column(name = "first_name", length = 255, nullable = false)
    open lateinit var firstName: String


    @Column(name = "last_name", length = 255, nullable = true)
    open var lastName: String? = null

    @Column(name = "address", length = 512)
    open var address: String? = null

    @OneToMany(
            cascade = [CascadeType.ALL],
            fetch = FetchType.LAZY,
            mappedBy = "contact"
    )
    open var phones: MutableSet<PhoneEntity> = mutableSetOf()

}